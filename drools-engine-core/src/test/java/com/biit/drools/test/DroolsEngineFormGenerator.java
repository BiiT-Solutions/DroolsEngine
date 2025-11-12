package com.biit.drools.test;

/*-
 * #%L
 * Drools Engine Core
 * %%
 * Copyright (C) 2022 - 2025 BiiT Sourcing Solutions S.L.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.dom4j.DocumentException;

import com.biit.drools.engine.DroolsRulesEngine;
import com.biit.drools.engine.exceptions.DroolsRuleExecutionException;
import com.biit.drools.engine.importer.OrbeonSubmittedAnswerImporter;
import com.biit.drools.form.DroolsForm;
import com.biit.drools.form.DroolsSubmittedForm;
import com.biit.form.submitted.ISubmittedForm;
import com.biit.utils.file.FileReader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

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
