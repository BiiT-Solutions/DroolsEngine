package com.biit.drools.configuration;

import com.biit.drools.logger.DroolsEngineLogger;
import com.biit.utils.configuration.ConfigurationReader;
import com.biit.utils.configuration.PropertiesSourceFile;
import com.biit.utils.configuration.SystemVariablePropertiesSourceFile;
import com.biit.utils.configuration.exceptions.PropertyNotFoundException;

public class DroolsEngineConfigurationReader extends ConfigurationReader {
	private static final String CONFIG_FILE = "settings.conf";
	private static final String PLUGINS_PATH_PROPERTY_NAME = "pluginsPath";
	private static final String DEFAULT_PLUGINS_PATH = "plugins/";
	private static final String DROOLS_SYSTEM_VARIABLE_CONFIG = "DROOLS_CONFIG";
	private static DroolsEngineConfigurationReader instance;

	private DroolsEngineConfigurationReader() {
		super();
		addProperty(PLUGINS_PATH_PROPERTY_NAME, DEFAULT_PLUGINS_PATH);
		addPropertiesSource(new PropertiesSourceFile(CONFIG_FILE));
		addPropertiesSource(new SystemVariablePropertiesSourceFile(DROOLS_SYSTEM_VARIABLE_CONFIG, CONFIG_FILE));
		readConfigurations();
	}

	public static DroolsEngineConfigurationReader getInstance() {
		if (instance == null) {
			synchronized (DroolsEngineConfigurationReader.class) {
				if (instance == null) {
					instance = new DroolsEngineConfigurationReader();
				}
			}
		}
		return instance;
	}

	@Override
	public String getProperty(String propertyId) {
		try {
			return super.getProperty(propertyId);
		} catch (PropertyNotFoundException e) {
			DroolsEngineLogger.errorMessage(this.getClass().getName(), e);
			return null;
		}
	}

	public String getPluginsPath() {
		return getProperty(PLUGINS_PATH_PROPERTY_NAME);
	}
}
