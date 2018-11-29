package com.biit.drools.test;

import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import org.pf4j.DefaultPluginManager;
import org.pf4j.JarPluginLoader;
import org.pf4j.ManifestPluginDescriptorFinder;
import org.pf4j.PluginDescriptorFinder;
import org.pf4j.PluginLoader;
import org.pf4j.PluginWrapper;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.biit.plugins.BasePlugin;

/**
 * For executing this test correctly the plugins must be placed in the specified
 * path by the settings.conf file
 * 
 */
@Test(groups = { "pluginsTest" })
public class PluginsTest extends DroolsEngineFormGenerator {

	private final static Class<?> PLUGIN_INTERFACE = com.biit.plugins.BasePlugin.class;
	private final static String HELLO_WORLD_PLUGIN_NAME = "Hello World";
	private final static String HELLO_WORLD_PLUGIN_RETURN = "Hello World";
	private final static String HELLO_WORLD_PLUGIN_METHOD = "methodHelloWorld";
	private final static String HELLO_WORLD_PLUGIN_DROOLS_FILE = "rules/helloWorldPlugin.drl";
	private final static String DROOLS_EXAMPLE_PLUGIN_DROOLS_FILE = "rules/droolsExamplePlugin.drl";
	private final static String LIFERAY_PLUGIN_DROOLS_FILE = "rules/USMO Appendix_v1.drl";

	private final static String PLUGINS_FOLDER = "src/test/plugins";
	private final static String PLUGIN_ID = "hello-world";

	@Test
	public void loadPlugin() {
		// create the plugin manager
		DefaultPluginManager pluginManager = new DefaultPluginManager(Paths.get(PLUGINS_FOLDER)) {

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

		System.out.println(" ##### " + pluginManager.getStartedPlugins());
		System.out.println(" ##### " + pluginManager.getPlugin(PLUGIN_ID));

		pluginManager.enablePlugin(PLUGIN_ID);

		PluginWrapper pluginWrapper = pluginManager.getPlugin(PLUGIN_ID);

		List<PluginWrapper> startedPlugins = pluginManager.getStartedPlugins();

		for (PluginWrapper plugin : startedPlugins) {
			String pluginId = plugin.getDescriptor().getPluginId();
			System.out.println(String.format("Extensions added by plugin '%s':", pluginId));
			Set<String> extensionClassNames = pluginManager.getExtensionClassNames(pluginId);
			for (String extension : extensionClassNames) {
				System.out.println("   " + extension);
			}
		}

		// IPLugin must be in the plugin. If not
		// java.lang.InstantiationException appears.
		// List<IPlugin> plugins = pluginManager.getExtensions(IPlugin.class);
		List<?> plugins = pluginManager.getExtensions(PLUGIN_ID);
		Assert.assertEquals(plugins.size(), 1);

		for (Object plugin : plugins) {
			Assert.assertEquals(((BasePlugin) plugin).getPluginMethods().size(), 2);
		}

		// stop and unload all plugins
		pluginManager.stopPlugins();
	}
	// @Test
	// public void helloWorldPluginSelectionTest1() throws
	// NoSuchMethodException, IllegalAccessException, IllegalArgumentException,
	// InvocationTargetException {
	// // Calling the first plugin
	// IPlugin pluginInterface =
	// PluginController.getInstance().getPlugin(PLUGIN_INTERFACE,
	// HELLO_WORLD_PLUGIN_NAME);
	// Assert.assertNotNull(pluginInterface);
	// Method method = ((IPlugin)
	// pluginInterface).getPluginMethod(HELLO_WORLD_PLUGIN_METHOD);
	// Assert.assertEquals(method.invoke(pluginInterface),
	// HELLO_WORLD_PLUGIN_RETURN);
	// }
	//
	// @Test
	// public void helloWorldPluginOneCallTest() {
	// // Calling the hello world plugin with only one call
	// Assert.assertEquals(PluginController.getInstance().executePluginMethod(PLUGIN_INTERFACE,
	// HELLO_WORLD_PLUGIN_NAME, HELLO_WORLD_PLUGIN_METHOD),
	// HELLO_WORLD_PLUGIN_RETURN);
	// }
	//
	// @Test
	// public void helloWorldPluginDroolsCallWithoutParametersTest() throws
	// FileNotFoundException, DroolsRuleExecutionException,
	// UnsupportedEncodingException,
	// DocumentException {
	// String drlFile = FileReader.getResource(HELLO_WORLD_PLUGIN_DROOLS_FILE,
	// StandardCharsets.UTF_8);
	// // Execution of the rules
	// DroolsForm droolsForm = runDroolsRules(drlFile);
	// Assert.assertNotNull(getSubmittedForm());
	// // Check result
	// Assert.assertEquals(((DroolsSubmittedForm)
	// droolsForm.getDroolsSubmittedForm()).getVariableValue("customVariableResult"),
	// "Hello World");
	// }
	//
	// @Test
	// public void helloWorldPluginDroolsCallWithParametersTest() throws
	// FileNotFoundException, DroolsRuleExecutionException,
	// UnsupportedEncodingException,
	// DocumentException {
	// String drlFile =
	// FileReader.getResource(DROOLS_EXAMPLE_PLUGIN_DROOLS_FILE,
	// StandardCharsets.UTF_8);
	// // Execution of the rules
	// DroolsForm droolsForm = runDroolsRules(drlFile);
	// Assert.assertNotNull(getSubmittedForm());
	// // Check result
	// Assert.assertEquals(((DroolsSubmittedForm)
	// droolsForm.getDroolsSubmittedForm()).getVariableValue("customVariableResult"),
	// 8.);
	// }
	//
	// @Test
	// public void LiferayArticleDroolsCall() throws FileNotFoundException,
	// DroolsRuleExecutionException, UnsupportedEncodingException,
	// DocumentException {
	// String drlFile = FileReader.getResource(LIFERAY_PLUGIN_DROOLS_FILE,
	// StandardCharsets.UTF_8);
	// // Execution of the rules
	// runDroolsRules(drlFile);
	// }
}
