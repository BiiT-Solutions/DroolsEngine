package com.biit.drools.configuration;

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

import java.nio.file.Path;

import com.biit.drools.logger.DroolsEngineLogger;
import com.biit.utils.configuration.ConfigurationReader;
import com.biit.utils.configuration.PropertiesSourceFile;
import com.biit.utils.configuration.SystemVariablePropertiesSourceFile;
import com.biit.utils.configuration.exceptions.PropertyNotFoundException;
import com.biit.utils.file.watcher.FileWatcher.FileModifiedListener;

public class DroolsEngineConfigurationReader extends ConfigurationReader {
	private static final String CONFIG_FILE = "settings.conf";
	private static final String DROOLS_SYSTEM_VARIABLE_CONFIG = "DROOLS_CONFIG";
	private static DroolsEngineConfigurationReader instance;

	private DroolsEngineConfigurationReader() {
		super();
	
	
		PropertiesSourceFile sourceFile = new PropertiesSourceFile(CONFIG_FILE);
		sourceFile.addFileModifiedListeners(new FileModifiedListener() {

			@Override
			public void changeDetected(Path pathToFile) {
				DroolsEngineLogger.info(this.getClass().getName(), "WAR settings file '" + pathToFile + "' change detected.");
				readConfigurations();
			}
		});
		addPropertiesSource(sourceFile);
		
		SystemVariablePropertiesSourceFile systemSourceFile = new SystemVariablePropertiesSourceFile(DROOLS_SYSTEM_VARIABLE_CONFIG, CONFIG_FILE);
		systemSourceFile.addFileModifiedListeners(new FileModifiedListener() {

			@Override
			public void changeDetected(Path pathToFile) {
				DroolsEngineLogger.info(this.getClass().getName(), "System variable settings file '" + pathToFile + "' change detected.");
				readConfigurations();
			}
		});
		addPropertiesSource(systemSourceFile);
		
		
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

}
