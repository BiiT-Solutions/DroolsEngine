package com.biit.drools.configuration;

import com.biit.drools.logger.DroolsEngineLogger;
import com.biit.utils.configuration.ConfigurationReader;
import com.biit.utils.configuration.PropertiesSourceFile;
import com.biit.utils.configuration.SystemVariablePropertiesSourceFile;
import com.biit.utils.configuration.exceptions.PropertyNotFoundException;
import com.biit.utils.file.watcher.FileWatcher.FileModifiedListener;

import java.nio.file.Path;

public final class DroolsEngineConfigurationReader extends ConfigurationReader {
    private static final String CONFIG_FILE = "settings.conf";
    private static final String DROOLS_SYSTEM_VARIABLE_CONFIG = "DROOLS_CONFIG";
    private static DroolsEngineConfigurationReader instance;

    private DroolsEngineConfigurationReader() {
        super();


        final PropertiesSourceFile sourceFile = new PropertiesSourceFile(CONFIG_FILE);
        sourceFile.addFileModifiedListeners(new FileModifiedListener() {

            @Override
            public void changeDetected(Path pathToFile) {
                DroolsEngineLogger.info(this.getClass().getName(), "WAR settings file '" + pathToFile + "' change detected.");
                readConfigurations();
            }
        });
        addPropertiesSource(sourceFile);

        final SystemVariablePropertiesSourceFile systemSourceFile = new SystemVariablePropertiesSourceFile(DROOLS_SYSTEM_VARIABLE_CONFIG, CONFIG_FILE);
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
