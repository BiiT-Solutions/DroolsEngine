package com.biit.drools.global.variables.json;

import com.biit.drools.global.variables.DroolsGlobalVariable;
import com.biit.drools.global.variables.DroolsVariableData;
import com.biit.drools.global.variables.DroolsVariableDataDate;
import com.biit.drools.global.variables.DroolsVariableDataNumber;
import com.biit.drools.global.variables.DroolsVariableDataPostalCode;
import com.biit.drools.global.variables.DroolsVariableDataText;
import com.biit.drools.logger.DroolsEngineLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class takes a java class and transforms it to a json string<br>
 * Used to convert the global variables array to a json string and store it
 */
public final class DroolsGlobalVariablesFromJson {

    private DroolsGlobalVariablesFromJson() {

    }

    public static List<DroolsGlobalVariable> fromJson(String jsonString) {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DroolsGlobalVariable.class, new DroolsGlobalVariableDeserializer());
        gsonBuilder.registerTypeAdapter(DroolsVariableData.class, new GenericVariableDataDeserializer<DroolsVariableData>());
        gsonBuilder.registerTypeAdapter(DroolsVariableDataNumber.class, new DroolsVariableDataNumberDeserializer());
        gsonBuilder.registerTypeAdapter(DroolsVariableDataText.class, new DroolsVariableDataTextDeserializer());
        gsonBuilder.registerTypeAdapter(DroolsVariableDataDate.class, new DroolsVariableDataDateDeserializer());
        gsonBuilder.registerTypeAdapter(DroolsVariableDataPostalCode.class, new DroolsVariableDataPostalCodeDeserializer());
        final Gson gson = gsonBuilder.create();
        final DroolsGlobalVariable[] variables = gson.fromJson(jsonString, DroolsGlobalVariable[].class);
        if (variables == null) {
            DroolsEngineLogger.warning(DroolsGlobalVariablesFromJson.class.getName(), "Global variables are null! Probably the file is not correct.");
            return new ArrayList<DroolsGlobalVariable>();
        }
        return Arrays.asList(variables);
    }
}
