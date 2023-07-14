package com.biit.drools.global.variables.serialization;

import com.biit.drools.global.variables.DroolsGlobalVariable;
import com.biit.form.jackson.serialization.ObjectMapperFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

/**
 * This class takes a java class and transforms it to a json string<br>
 * Used to convert the global variables array to a json string and store it
 */
public final class DroolsGlobalVariablesFromJson {

    private DroolsGlobalVariablesFromJson() {

    }

    public static List<DroolsGlobalVariable> fromJson(String jsonString) {
        try {
            return ObjectMapperFactory.getObjectMapper().readValue(jsonString, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
