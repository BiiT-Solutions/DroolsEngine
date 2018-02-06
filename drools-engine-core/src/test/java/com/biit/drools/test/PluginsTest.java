package com.biit.drools.test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import org.dom4j.DocumentException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.biit.drools.engine.exceptions.DroolsRuleExecutionException;
import com.biit.drools.engine.plugins.PluginController;
import com.biit.drools.form.DroolsForm;
import com.biit.drools.form.DroolsSubmittedForm;
import com.biit.plugins.interfaces.IPlugin;
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
	private final static String LIFERAY_PLUGIN_DROOLS_FILE = "rules/USMO Appendix_v1.drl";

	@Test(groups = { "pluginsTest" })
	public void helloWorldPluginSelectionTest1() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// Calling the first plugin
		IPlugin pluginInterface = PluginController.getInstance().getPlugin(PLUGIN_INTERFACE, HELLO_WORLD_PLUGIN_NAME);
		Assert.assertNotNull(pluginInterface);
		Method method = ((IPlugin) pluginInterface).getPluginMethod(HELLO_WORLD_PLUGIN_METHOD);
		Assert.assertEquals(method.invoke(pluginInterface), HELLO_WORLD_PLUGIN_RETURN);
	}

	@Test(groups = { "pluginsTest" })
	public void helloWorldPluginOneCallTest() {
		// Calling the hello world plugin with only one call
		Assert.assertEquals(PluginController.getInstance().executePluginMethod(PLUGIN_INTERFACE, HELLO_WORLD_PLUGIN_NAME, HELLO_WORLD_PLUGIN_METHOD),
				HELLO_WORLD_PLUGIN_RETURN);
	}

	@Test(groups = { "pluginsTest" })
	public void helloWorldPluginDroolsCallWithoutParametersTest() throws FileNotFoundException, DroolsRuleExecutionException, UnsupportedEncodingException,
			DocumentException {
		String drlFile = FileReader.getResource(HELLO_WORLD_PLUGIN_DROOLS_FILE, StandardCharsets.UTF_8);
		// Execution of the rules
		DroolsForm droolsForm = runDroolsRules(drlFile);
		Assert.assertNotNull(getSubmittedForm());
		// Check result
		Assert.assertEquals(((DroolsSubmittedForm) droolsForm.getDroolsSubmittedForm()).getVariableValue("customVariableResult"), "Hello World");
	}

	@Test(groups = { "pluginsTest" })
	public void helloWorldPluginDroolsCallWithParametersTest() throws FileNotFoundException, DroolsRuleExecutionException, UnsupportedEncodingException,
			DocumentException {
		String drlFile = FileReader.getResource(DROOLS_EXAMPLE_PLUGIN_DROOLS_FILE, StandardCharsets.UTF_8);
		// Execution of the rules
		DroolsForm droolsForm = runDroolsRules(drlFile);
		Assert.assertNotNull(getSubmittedForm());
		// Check result
		Assert.assertEquals(((DroolsSubmittedForm) droolsForm.getDroolsSubmittedForm()).getVariableValue("customVariableResult"), 8.);
	}

	@Test(groups = { "pluginsTest" })
	public void LiferayArticleDroolsCall() throws FileNotFoundException, DroolsRuleExecutionException, UnsupportedEncodingException, DocumentException {
		String drlFile = FileReader.getResource(LIFERAY_PLUGIN_DROOLS_FILE, StandardCharsets.UTF_8);
		// Execution of the rules
		runDroolsRules(drlFile);
	}
}
