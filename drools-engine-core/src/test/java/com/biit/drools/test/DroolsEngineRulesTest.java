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

import com.biit.drools.engine.exceptions.DroolsRuleExecutionException;
import com.biit.drools.form.DroolsForm;
import com.biit.drools.form.DroolsSubmittedForm;
import com.biit.utils.file.FileReader;
import org.dom4j.DocumentException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * Tests the rule loading from a static file<br>
 * Needs the files kidScreen.xml and droolsRulesFileTest.drl in test/resources
 */
public class DroolsEngineRulesTest extends DroolsEngineFormGenerator {

    private final static String DROOLS_RULES_PATH = "rules/droolsRulesFileTest.drl";

    @Test(groups = {"droolsEngineRules"})
    public void rulesTest() throws DroolsRuleExecutionException, FileNotFoundException, UnsupportedEncodingException, DocumentException {
        String drlFile = FileReader.getResource(DROOLS_RULES_PATH, StandardCharsets.UTF_8);
        // Execution of the rules
        DroolsForm droolsForm = runDroolsRules(drlFile);
        Assert.assertNotNull(getSubmittedForm());
        Assert.assertNotNull(droolsForm);
        // Check result
        Assert.assertNotNull((droolsForm.getDroolsSubmittedForm()));
        Assert.assertEquals(((DroolsSubmittedForm) droolsForm.getDroolsSubmittedForm()).getVariableValue("customVariableResult"), 11.);
    }
}
