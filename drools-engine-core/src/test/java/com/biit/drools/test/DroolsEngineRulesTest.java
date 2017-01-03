package com.biit.drools.test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.dom4j.DocumentException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.biit.drools.engine.DroolsRuleFile;
import com.biit.drools.engine.exceptions.DroolsRuleExecutionException;
import com.biit.drools.form.DroolsForm;
import com.biit.drools.form.DroolsSubmittedForm;

/**
 * Tests the rule loading from a static file<br>
 * Needs the files kidScreen.xml and droolsRulesFileTest.drl in test/resources
 */
public class DroolsEngineRulesTest extends DroolsEngineFormGenerator {

	private final static String DROOLS_RULES_PATH = "rules/droolsRulesFileTest.drl";

	@Test(groups = { "droolsEngineRules" })
	public void rulesTest() throws DroolsRuleExecutionException, FileNotFoundException, UnsupportedEncodingException, DocumentException {
		// Execution of the rules
		DroolsForm droolsForm = runDroolsRules(new DroolsRuleFile(DROOLS_RULES_PATH));
		Assert.assertNotNull(getSubmittedForm());
		Assert.assertNotNull(droolsForm);
		// Check result
		Assert.assertNotNull(((DroolsSubmittedForm) droolsForm.getDroolsSubmittedForm()));
		Assert.assertEquals(((DroolsSubmittedForm) droolsForm.getDroolsSubmittedForm()).getVariableValue("customVariableResult"), 11.);
	}
}
