package com.biit.drools.test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.biit.drools.KieManager;
import com.biit.drools.form.DroolsForm;
import com.biit.drools.form.DroolsSubmittedForm;
import com.biit.drools.importer.OrbeonSubmittedAnswerImporter;
import com.biit.drools.logger.DroolsEngineLogger;
import com.biit.form.submitted.ISubmittedForm;
import com.biit.utils.file.FileReader;

/**
 * Tests the rule loading from a static file<br>
 * Needs the files kidScreen.xml and droolsRulesFileTest.drl in test/resources
 */
public class DroolsEngineRulesTest {

	private final static String APP = "Application1";
	private final static String FORM_NAME = "Form1";
	private final static String FORM_VERSION = "1";
	private final static String INPUT_XML_PATH = "kidScreen.xml";
	private final static String DROOLS_RULES_PATH = "droolsRulesFileTest.drl";
	
	private ISubmittedForm submittedForm;
	private OrbeonSubmittedAnswerImporter orbeonImporter = new OrbeonSubmittedAnswerImporter();

	private void createSubmittedForm() {
		try {
			setSubmittedForm(new DroolsSubmittedForm(APP, FORM_NAME, FORM_VERSION));
			String xmlFile = FileReader.getResource(INPUT_XML_PATH, StandardCharsets.UTF_8);
			getOrbeonImporter().readXml(xmlFile, getSubmittedForm());
		} catch (Exception e) {
			DroolsEngineLogger.errorMessage(this.getClass().getName(), e);
		}
	}

	private DroolsForm runDroolsRules(String drlFile) {
		// Generate the drools rules.
		try {
			DroolsEngineLogger.debug(this.getClass().getName(), drlFile);
			Files.write(Paths.get(System.getProperty("java.io.tmpdir") + File.separator + "generatedRules.drl"),
					drlFile.getBytes("UTF-8"));
			createSubmittedForm();
			// Launch kie
			KieManager km = new KieManager();
			// Load the rules in memory
			km.buildSessionRules(drlFile);
			// Creation of the global constants
			km.setGlobalVariables(null);
			DroolsForm droolsForm = new DroolsForm(getSubmittedForm());
			km.setFacts(Arrays.asList((ISubmittedForm) droolsForm));
			km.execute();
			return droolsForm;
		} catch (Exception e) {
			DroolsEngineLogger.errorMessage(this.getClass().getName(), e);
		}
		return null;
	}

	private ISubmittedForm getSubmittedForm() {
		return submittedForm;
	}

	private void setSubmittedForm(ISubmittedForm submittedForm) {
		this.submittedForm = submittedForm;
	}

	private OrbeonSubmittedAnswerImporter getOrbeonImporter() {
		return orbeonImporter;
	}

	@Test(groups = { "droolsEngineRules" })
	public void rulesTest() {
		try {
			String drlFile = FileReader.getResource(DROOLS_RULES_PATH, StandardCharsets.UTF_8);
			// Execution of the rules
			DroolsForm droolsForm = runDroolsRules(drlFile);
			if (submittedForm != null) {
				// Check result
				Assert.assertEquals(((DroolsSubmittedForm) droolsForm.getDroolsSubmittedForm())
						.getVariableValue("customVariableResult"), 11.);
			} else {
				Assert.fail();
			}
		} catch (Exception e) {
			DroolsEngineLogger.errorMessage(this.getClass().getName(), e);
		}
	}
}
