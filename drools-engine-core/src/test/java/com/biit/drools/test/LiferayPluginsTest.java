package com.biit.drools.test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

import org.dom4j.DocumentException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.biit.drools.engine.exceptions.DroolsRuleExecutionException;
import com.biit.plugins.PluginController;
import com.biit.plugins.exceptions.DuplicatedPluginFoundException;
import com.biit.plugins.exceptions.NoPluginFoundException;
import com.biit.plugins.interfaces.IPlugin;
import com.biit.utils.file.FileReader;

/**
 * For executing this test correctly the plugins must be placed in the specified
 * path by the settings.conf file
 * 
 */
@Test(groups = { "liferayPluginsTest" })
public class LiferayPluginsTest extends DroolsEngineFormGenerator {
	private final static String PLUGIN_ID = "liferay-article";
	private final static String LIFERAY_PLUGIN_DROOLS_FILE = "rules/USMO Appendix_v1.drl";

	@Test
	public void helloWorldPluginSelectionTest1() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoPluginFoundException, DuplicatedPluginFoundException {
		// Calling the first plugin
		IPlugin pluginInterface = PluginController.getInstance().getPlugin(IPlugin.class, PLUGIN_ID);
		Assert.assertNotNull(pluginInterface);
	}

	@Test
	public void LiferayArticleDroolsCall() throws FileNotFoundException, DroolsRuleExecutionException, UnsupportedEncodingException, DocumentException {
		String drlFile = FileReader.getResource(LIFERAY_PLUGIN_DROOLS_FILE, StandardCharsets.UTF_8);
		// Execution of the rules
		runDroolsRules(drlFile);
	}
}
