package com.biit.drools.test;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.biit.drools.logger.DroolsEngineLogger;
import com.biit.drools.plugins.PluginController;
import com.biit.utils.file.FileReader;

/**
 * For executing this test correctly the plugins must be placed in the specified
 * path by the settings.conf file
 * 
 */
public class PluginsTest extends DroolsEngineFormGenerator {

	private final static Class<?> PLUGIN_INTERFACE = com.biit.plugins.interfaces.IPlugin.class;
	private final static String HELLO_WORLD_PLUGIN_NAME = "HelloWorld";
	private final static String HELLO_WORLD_PLUGIN_RETURN = "Hello World";
	private final static String HELLO_WORLD_PLUGIN_METHOD = "methodHelloWorld";
	private final static String HELLO_WORLD_PLUGIN_DROOLS_FILE = "rules/helloWorldPlugin.drl";
	private final static String DROOLS_EXAMPLE_PLUGIN_DROOLS_FILE = "rules/droolsExamplePlugin.drl";

	@Test(groups = { "pluginsTest" })
	public void helloWorldPluginSelectionTest1() {
		try {
			// Calling the first plugin
			IPlugin pluginInterface = PluginController.getInstance().getPlugin(PLUGIN_INTERFACE,
					HELLO_WORLD_PLUGIN_NAME);
			Method method = ((IPlugin) pluginInterface).getPluginMethod(HELLO_WORLD_PLUGIN_METHOD);
			Assert.assertEquals(method.invoke(pluginInterface), HELLO_WORLD_PLUGIN_RETURN);
		} catch (Exception e) {
			Assert.fail("Exception in test");
		}
	}

	@Test(groups = { "pluginsTest" })
	public void helloWorldPluginOneCallTest() {
		try {
			// Calling the hello world plugin with only one call
			Assert.assertEquals(
					PluginController.getInstance().executePluginMethod(PLUGIN_INTERFACE, HELLO_WORLD_PLUGIN_NAME,
							HELLO_WORLD_PLUGIN_METHOD), HELLO_WORLD_PLUGIN_RETURN);
		} catch (Exception e) {
			Assert.fail("Exception in test");
		}
	}

	@Test(groups = { "pluginsTest" })
	public void helloWorldPluginDroolsCallWithoutParametersTest() {
		try {
			String drlFile = FileReader.getResource(HELLO_WORLD_PLUGIN_DROOLS_FILE, StandardCharsets.UTF_8);
			// Execution of the rules
			DroolsForm droolsForm = runDroolsRules(drlFile);
			if (getSubmittedForm() != null) {
				// Check result
				Assert.assertEquals(((DroolsSubmittedForm) droolsForm.getDroolsSubmittedForm())
						.getVariableValue("customVariableResult"), "Hello World");
			} else {
				Assert.fail();
			}
		} catch (Exception e) {
			DroolsEngineLogger.errorMessage(this.getClass().getName(), e);
			Assert.fail();
		}
	}

	@Test(groups = { "pluginsTest" })
	public void helloWorldPluginDroolsCallWithParametersTest() {
		try {
			String drlFile = FileReader.getResource(DROOLS_EXAMPLE_PLUGIN_DROOLS_FILE, StandardCharsets.UTF_8);
			// Execution of the rules
			DroolsForm droolsForm = runDroolsRules(drlFile);
			if (getSubmittedForm() != null) {
				// Check result
				Assert.assertEquals(((DroolsSubmittedForm) droolsForm.getDroolsSubmittedForm())
						.getVariableValue("customVariableResult"), 8.);
			} else {
				Assert.fail();
			}
		} catch (Exception e) {
			DroolsEngineLogger.errorMessage(this.getClass().getName(), e);
			Assert.fail();
		}
	}
}
