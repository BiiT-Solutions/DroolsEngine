package com.biit.drools.test;

import com.biit.drools.engine.exceptions.DroolsRuleExecutionException;
import com.biit.drools.form.DroolsForm;
import com.biit.drools.form.DroolsSubmittedForm;
import com.biit.plugins.BasePlugin;
import com.biit.plugins.PluginController;
import com.biit.plugins.exceptions.DuplicatedPluginFoundException;
import com.biit.plugins.interfaces.IPlugin;
import com.biit.plugins.interfaces.exceptions.InvalidMethodParametersException;
import com.biit.plugins.interfaces.exceptions.NoMethodFoundException;
import com.biit.plugins.interfaces.exceptions.NoPluginFoundException;
import com.biit.utils.file.FileReader;
import org.dom4j.DocumentException;
import org.pf4j.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

/**
 * For executing this test correctly the plugins must be placed in the specified
 * path by the settings.conf file
 * 
 */
@Test(groups = { "pluginsTest" })
public class PluginsTest extends DroolsEngineFormGenerator {

	private final static String HELLO_WORLD_PLUGIN_RETURN = "Hello World";
	private final static String HELLO_WORLD_PLUGIN_METHOD = "methodHelloWorld";
	private final static String HELLO_WORLD_PLUGIN_DROOLS_FILE = "rules/helloWorldPlugin.drl";
	private final static String DROOLS_EXAMPLE_PLUGIN_DROOLS_FILE = "rules/droolsExamplePlugin.drl";
	private final static String LIFERAY_PLUGIN_DROOLS_FILE = "rules/USMO Appendix_v1.drl";

	private final static String PLUGINS_FOLDER = "src/test/plugins";
	private final static String PLUGIN_ID = "hello-world";

	private DefaultPluginManager pluginManager;

	@BeforeClass
	public void loadPlugin() {
		// create the plugin manager
		pluginManager = new DefaultPluginManager(Paths.get(PLUGINS_FOLDER)) {

			@Override
			protected PluginLoader createPluginLoader() {
				// load only jar plugins
				return new JarPluginLoader(this);
			}

			@Override
			protected PluginDescriptorFinder createPluginDescriptorFinder() {
				// read plugin descriptor from jar's manifest
				return new ManifestPluginDescriptorFinder();
			}
		};
		// start and load all plugins of application
		pluginManager.loadPlugins();
		pluginManager.startPlugins();
	}

	@Test
	public void checkPluginHelloWorldById() {
		List<?> plugins = pluginManager.getExtensions(PLUGIN_ID);
		Assert.assertEquals(plugins.size(), 1);

		for (Object plugin : plugins) {
			Assert.assertEquals(((BasePlugin) plugin).getPluginMethods().size(), 3);
		}
	}

	@Test
	public void checkLoadedPluginsByClass() {
		List<IPlugin> plugins = pluginManager.getExtensions(IPlugin.class);
		Assert.assertEquals(plugins.size(), 4);
	}

	@Test
	public void helloWorldPluginSelectionTest1() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoPluginFoundException, DuplicatedPluginFoundException {
		// Calling the first plugin
		IPlugin pluginInterface = PluginController.getInstance().getPlugin(IPlugin.class, PLUGIN_ID);
		Assert.assertNotNull(pluginInterface);
		Method method = ((IPlugin) pluginInterface).getPluginMethod(HELLO_WORLD_PLUGIN_METHOD);
		Assert.assertEquals(method.invoke(pluginInterface), HELLO_WORLD_PLUGIN_RETURN);
	}

	@Test
	public void helloWorldPluginOneCallTest() throws NoPluginFoundException, DuplicatedPluginFoundException {
		// Calling the hello world plugin with only one call
		Assert.assertEquals(PluginController.getInstance().executePluginMethod(IPlugin.class, PLUGIN_ID, HELLO_WORLD_PLUGIN_METHOD), HELLO_WORLD_PLUGIN_RETURN);
	}

	@Test
	public void helloWorldPluginDroolsCallWithoutParametersTest() throws FileNotFoundException, DroolsRuleExecutionException, UnsupportedEncodingException,
			DocumentException {
		String drlFile = FileReader.getResource(HELLO_WORLD_PLUGIN_DROOLS_FILE, StandardCharsets.UTF_8);
		// Execution of the rules
		DroolsForm droolsForm = runDroolsRules(drlFile);
		Assert.assertNotNull(getSubmittedForm());
		// Check result
		Assert.assertEquals(((DroolsSubmittedForm) droolsForm.getDroolsSubmittedForm()).getVariableValue("customVariableResult"), "Hello World");
	}

	@Test
	public void helloWorldPluginDroolsCallWithParametersTest() throws FileNotFoundException, DroolsRuleExecutionException, UnsupportedEncodingException,
			DocumentException {
		String drlFile = FileReader.getResource(DROOLS_EXAMPLE_PLUGIN_DROOLS_FILE, StandardCharsets.UTF_8);
		// Execution of the rules
		DroolsForm droolsForm = runDroolsRules(drlFile);
		Assert.assertNotNull(getSubmittedForm());
		// Check result
		Assert.assertEquals(((DroolsSubmittedForm) droolsForm.getDroolsSubmittedForm()).getVariableValue("customVariableResult"), 8.0);
	}

	@Test
	public void LiferayArticleDroolsCall() throws FileNotFoundException, DroolsRuleExecutionException, UnsupportedEncodingException, DocumentException {
		String drlFile = FileReader.getResource(LIFERAY_PLUGIN_DROOLS_FILE, StandardCharsets.UTF_8);
		// Execution of the rules
		runDroolsRules(drlFile);
	}

	@AfterClass
	public void stopPlugins() {
		// stop and unload all plugins
		pluginManager.stopPlugins();
		pluginManager.unloadPlugin(PLUGIN_ID);
	}
}
