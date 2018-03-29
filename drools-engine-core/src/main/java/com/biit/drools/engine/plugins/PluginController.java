package com.biit.drools.engine.plugins;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;

import com.biit.drools.configuration.DroolsEngineConfigurationReader;
import com.biit.drools.logger.DroolsEngineLogger;
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
	private PluginManagerUtil pluginManagerUtil;
	private Collection<IPlugin> availablePlugins;
	private Map<Class<?>, Collection<IPlugin>> pluginsByName;

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
		pluginManager = PluginManagerFactory.createPluginManager();
		pluginManagerUtil = new PluginManagerUtil(pluginManager);
		pluginsByName = new HashMap<>();
		scanForPlugins();
	}

	/**
	 * Scans for new plugins in the specified path of the configuration file.
	 */
	public void scanForPlugins() {
		String folderToScan = DroolsEngineConfigurationReader.getInstance().getPluginsPath();
		DroolsEngineLogger.debug(this.getClass().getName(), "Scanning folder '" + folderToScan + "' for plugins.");
		// If too short, plugin library launch Caused by:
		// java.lang.StringIndexOutOfBoundsException: String index out of range:
		// 4 at java.lang.String.substring(String.java:1907) at
		// net.xeoh.plugins.base.impl.classpath.loader.FileLoader.loadFrom(FileLoader.java:83)
		if (folderToScan != null && folderToScan.length() > 4) {
			pluginManager.addPluginsFrom(new File(folderToScan).toURI());
		}
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
	public Collection<IPlugin> getAllPlugins() {
		if (availablePlugins == null) {
			availablePlugins = pluginManagerUtil.getPlugins(IPlugin.class);
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
	public Collection<IPlugin> getAllPlugins(Class pluginInterface) {
		if (pluginsByName.get(pluginInterface) == null) {
			pluginsByName.put(pluginInterface, pluginManagerUtil.getPlugins(pluginInterface));
		}
		return pluginsByName.get(pluginInterface);
	}

	/**
	 * Returns all the plugins that implement the passed class name
	 * 
	 * @param interfaceName
	 *            the interface that defines the plugin.
	 * @return all available plugins that implements the selected interface.
	 */
	public Collection<IPlugin> getAllPlugins(String interfaceName) {
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Plugin getPlugin(Class pluginInterface) {
		return pluginManagerUtil.getPlugin(pluginInterface);
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
	public Plugin getPlugin(String interfaceName) {
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
		Collection<IPlugin> plugins = getAllPlugins(interfaceName);
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
