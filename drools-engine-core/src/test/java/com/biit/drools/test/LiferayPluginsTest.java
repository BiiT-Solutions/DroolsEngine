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

import com.biit.plugins.configuration.PluginConfigurationReader;
import com.biit.plugins.interfaces.IStandardPlugin;
import com.biit.plugins.interfaces.exceptions.NoPluginFoundException;
import org.dom4j.DocumentException;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.biit.drools.engine.exceptions.DroolsRuleExecutionException;
import com.biit.plugins.PluginController;
import com.biit.plugins.exceptions.DuplicatedPluginFoundException;
import com.biit.plugins.interfaces.IPlugin;
import com.biit.utils.file.FileReader;

/**
 * For executing this test correctly the plugins must be placed in the specified
 * path by the settings.conf file
 */
@SpringBootTest
@Test(groups = { "liferayPluginsTest" })
public class LiferayPluginsTest extends DroolsEngineFormGenerator {
    private final static String LIFERAY_PLUGIN_NAME = "liferay-article";
    private final static String LIFERAY_PLUGIN_DROOLS_FILE = "rules/USMO Appendix_v1.drl";
    private final static String LIFERAY_PLUGIN_METHOD = "methodGetLatestArticleContentByProperty";
    private final static String LIFERAY_ARTICLE_PROPERTY = "Appendix-Antropometrie";
    private final static String LIFERAY_PLUGIN_RETURN = "A short description to explain the graphics below and what is this intended to achieve.";

    static {
        //Set the system environment.
        System.setProperty(PluginConfigurationReader.SYSTEM_VARIABLE_PLUGINS_CONFIG_FOLDER, "src/test/plugins");
    }

    @Test
    public void helloWorldPluginSelectionTest1() throws NoPluginFoundException, DuplicatedPluginFoundException {
        // Calling the first plugin
        IPlugin pluginInterface = PluginController.getInstance().getPlugin(IStandardPlugin.class, LIFERAY_PLUGIN_NAME);
        Assert.assertNotNull(pluginInterface);
    }

    // Test disabled as the article in jenkins has a different ID that in testing
    @Test(enabled = false)
    public void liferayPluginDroolsCallWithParametersTest()
            throws NoPluginFoundException, DuplicatedPluginFoundException {
        // Calling the hello world plugin with only one call
        Assert.assertEquals(PluginController.getInstance().executePluginMethod(IStandardPlugin.class, LIFERAY_PLUGIN_NAME,
                LIFERAY_PLUGIN_METHOD, LIFERAY_ARTICLE_PROPERTY), LIFERAY_PLUGIN_RETURN);
    }

    @Test
    public void LiferayArticleDroolsCall() throws FileNotFoundException, DroolsRuleExecutionException, UnsupportedEncodingException, DocumentException {
        String drlFile = FileReader.getResource(LIFERAY_PLUGIN_DROOLS_FILE, StandardCharsets.UTF_8);
        // Execution of the rules
        runDroolsRules(drlFile);
    }
}
