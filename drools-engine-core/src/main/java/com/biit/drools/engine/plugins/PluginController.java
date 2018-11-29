package com.biit.drools.engine.plugins;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pf4j.DefaultPluginManager;
import org.pf4j.JarPluginLoader;
import org.pf4j.ManifestPluginDescriptorFinder;
import org.pf4j.PluginDescriptorFinder;
import org.pf4j.PluginLoader;
import org.pf4j.PluginManager;

import com.biit.drools.configuration.DroolsEngineConfigurationReader;
import com.biit.drools.logger.DroolsEngineLogger;
import com.biit.plugins.BasePlugin;
import com.biit.plugins.exceptions.InvalidMethodParametersException;
import com.biit.plugins.exceptions.MethodInvocationException;
import com.biit.plugins.exceptions.NoMethodFoundException;
import com.biit.plugins.interfaces.IPlugin;

/**
 * Singleton in charge of managing the plugins of the application
 * 
 */
public class PluginController {
	private static PluginController instance = new PluginController();
	private PluginManager pluginManager;
	private Collection<BasePlugin> availablePlugins;
	private Map<Class<?>, Collection<BasePlugin>> pluginsByClass;

	public static PluginController getInstance() {
		return instance;
	}

	/**
	 * Override of the clone method to avoid creating more than one instance
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	private PluginController() {
		// create the plugin manager
		String folderToScan = DroolsEngineConfigurationReader.getInstance().getPluginsPath();
		DroolsEngineLogger.debug(this.getClass().getName(), "Scanning folder '" + folderToScan + "' for plugins.");
		pluginManager = new DefaultPluginManager(Paths.get(folderToScan)) {

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

		pluginsByClass = new HashMap<>();
		// start and load all plugins of application
		pluginManager.loadPlugins();
		pluginManager.startPlugins();

		List<IPlugin> greetings = pluginManager.getExtensions(IPlugin.class);
		System.out.println("#~############################## " + greetings.size());
		System.out.println(greetings.get(0).getClass());
		System.out.println(String.format("Found %d extensions for extension point '%s'", greetings.size(), IPlugin.class.getName()));
	}

	private File[] getAllJars(String folderToScan) {
		File dir = new File(folderToScan);
		return dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar");
			}
		});
	}

	public boolean existsPlugins() {
		if ((getAllPlugins() != null) && (!getAllPlugins().isEmpty())) {
			return true;
		}
		return false;
	}

	private Class<?> getInterfaceClass(String interfaceName) {
		try {
			return Class.forName(interfaceName);
		} catch (ClassNotFoundException e) {
			DroolsEngineLogger.errorMessage(this.getClass().getName(), e);
		}
		return null;
	}

	/**
	 * Returns all the plugins that are loaded
	 * 
	 * @return all available plugins.
	 */
	public Collection<BasePlugin> getAllPlugins() {
		if (availablePlugins == null) {
			availablePlugins = pluginManager.getExtensions(BasePlugin.class);
		}
		return availablePlugins;
	}

	/**
	 * Returns all the plugins that implement the passed interface
	 * 
	 * @param pluginInterface
	 *            the interface that defines the plugin.
	 * @return all available plugins of the selected class.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection<BasePlugin> getAllPlugins(Class pluginInterface) {
		if (pluginsByClass.get(pluginInterface) == null) {
			pluginsByClass.put(pluginInterface, pluginManager.getExtensions(pluginInterface));
		}
		return pluginsByClass.get(pluginInterface);
	}

	/**
	 * Returns all the plugins that implement the passed class name
	 * 
	 * @param interfaceName
	 *            the interface that defines the plugin.
	 * @return all available plugins that implements the selected interface.
	 */
	public Collection<BasePlugin> getAllPlugins(String interfaceName) {
		return getAllPlugins(getInterfaceClass(interfaceName));
	}

	/**
	 * Returns the plugin specified by the class passed (the class can be an
	 * interface).<br>
	 * If several plugins implement the same class, one of them is selected
	 * randomly.
	 * 
	 * @param pluginInterface
	 *            the interface that defines the plugin.
	 * @return the plugin
	 */
	public IPlugin getPlugin(Class<?> pluginInterface) {
		return getAllPlugins(pluginInterface).iterator().next();
	}

	/**
	 * Returns the plugin specified by the class name passed (the class can be
	 * an interface).<br>
	 * If several plugins implement the same class name, one of them is selected
	 * randomly.
	 * 
	 * @param interfaceName
	 *            the interface that defines the plugin.
	 * @return the plugin.
	 */
	public IPlugin getPlugin(String interfaceName) {
		return getPlugin(getInterfaceClass(interfaceName));
	}

	/**
	 * Returns the plugin that matches the interface and the plugin name passed
	 * 
	 * @param interfaceName
	 *            the plugin interface
	 * @param pluginName
	 *            the plugin name
	 * @return the plugin.
	 */
	public IPlugin getPlugin(Class<?> interfaceName, String pluginName) {
		Collection<BasePlugin> plugins = getAllPlugins(interfaceName);
		for (IPlugin plugin : plugins) {
			if (plugin.getPluginName().equals(pluginName)) {
				return plugin;
			}
		}
		return null;
	}

	/**
	 * Returns the plugin that matches the interface name and the plugin name
	 * passed as strings
	 * 
	 * @param interfaceName
	 *            the plugin interface
	 * @param pluginName
	 *            the plugin name
	 * @return the plugin.
	 */
	public IPlugin getPlugin(String interfaceName, String pluginName) {
		return getPlugin(getInterfaceClass(interfaceName), pluginName);
	}

	/**
	 * Executes the method of the plugin specified.<br>
	 * It takes any number of parameters and passes them to the method
	 * invocation.
	 * 
	 * @param interfaceName
	 *            interface of the plugin.
	 * @param pluginName
	 *            name of the plugin.
	 * @param methodName
	 *            method to be used.
	 * @param parameters
	 *            parameters of the method.
	 * @return the result of the execution of the plugin method.
	 */
	public Object executePluginMethod(Class<?> interfaceName, String pluginName, String methodName, Object... parameters) {
		try {
			DroolsEngineLogger.debug(this.getClass().getName(), "Executing '" + methodName + "' with parameters '" + Arrays.toString(parameters) + "'.");
			IPlugin pluginInterface = getPlugin(interfaceName, pluginName);
			return pluginInterface.executeMethod(methodName, parameters);
		} catch (IllegalArgumentException | NoMethodFoundException | InvalidMethodParametersException | MethodInvocationException e) {
			StringBuilder sb = new StringBuilder();
			for (Object parameter : parameters) {
				sb.append(parameter + " (" + parameter.getClass().getName() + ")");
			}
			DroolsEngineLogger.severe(this.getClass().getName(), "No plugin method found '" + methodName + "' with parameters '" + sb.toString() + "'.");
			DroolsEngineLogger.errorMessage(this.getClass().getName(), e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Executes the method of the plugin specified.<br>
	 * It takes any number of parameters and passes them to the method
	 * invocation.
	 * 
	 * @param interfaceName
	 *            interface of the plugin.
	 * @param pluginName
	 *            name of the plugin.
	 * @param methodName
	 *            method to be used.
	 * @param parameters
	 *            parameters of the method.
	 * @return the result of the execution of the plugin method.
	 */
	public Object executePluginMethod(String interfaceName, String pluginName, String methodName, Object... parameters) {
		return executePluginMethod(getInterfaceClass(interfaceName), pluginName, methodName, parameters);
	}

}
