package com.biit.drools.test;

import com.biit.drools.engine.DroolsRulesEngine;
import com.biit.drools.engine.exceptions.DroolsRuleExecutionException;
import com.biit.drools.engine.importer.OrbeonSubmittedAnswerImporter;
import com.biit.drools.form.DroolsForm;
import com.biit.drools.form.DroolsSubmittedForm;
import com.biit.form.submitted.ISubmittedForm;
import com.biit.utils.file.FileReader;
import org.dom4j.DocumentException;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class DroolsEngineFormGenerator extends AbstractTestNGSpringContextTests {

    private final static String APP = "Application1";
    private final static String FORM_NAME = "Form1";
    private final static String INPUT_XML_PATH = "kidScreen.xml";
    private final static String INPUT_STRUCTURE_XML_PATH = "kidScreen.xhtml";

    private ISubmittedForm submittedForm;
    private OrbeonSubmittedAnswerImporter orbeonImporter = new OrbeonSubmittedAnswerImporter();

    private void createSubmittedForm() throws DocumentException, UnsupportedEncodingException, FileNotFoundException {
        setSubmittedForm(new DroolsSubmittedForm(APP, FORM_NAME));
        String xmlFile = FileReader.getResource(INPUT_XML_PATH, StandardCharsets.UTF_8);
        String orbeonStructureFile = FileReader.getResource(INPUT_STRUCTURE_XML_PATH, StandardCharsets.UTF_8);
        getOrbeonImporter().setOrbeonStructure(orbeonStructureFile);
        getOrbeonImporter().readXml(xmlFile, getSubmittedForm());

    }

    public DroolsForm runDroolsRules(String drlFile) throws DroolsRuleExecutionException, UnsupportedEncodingException, FileNotFoundException,
            DocumentException {
        // Generate the drools rules.
        createSubmittedForm();
        DroolsRulesEngine droolsEngine = new DroolsRulesEngine();
        return droolsEngine.applyDrools(getSubmittedForm(), drlFile, null);
    }

    protected ISubmittedForm getSubmittedForm() {
        return submittedForm;
    }

    private void setSubmittedForm(ISubmittedForm submittedForm) {
        this.submittedForm = submittedForm;
    }

    private OrbeonSubmittedAnswerImporter getOrbeonImporter() {
        return orbeonImporter;
    }
}
