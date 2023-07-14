package com.biit.drools.global.variables.serialization;

import com.biit.drools.global.variables.DroolsGlobalVariable;
import com.biit.drools.global.variables.interfaces.IVariableData;
import com.biit.drools.global.variables.type.DroolsGlobalVariableFormat;
import com.biit.form.jackson.serialization.ObjectMapperFactory;
import com.biit.form.log.FormStructureLogger;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class DroolsGlobalVariableDeserializer extends StdDeserializer<DroolsGlobalVariable> {

    public DroolsGlobalVariableDeserializer() {
        super(DroolsGlobalVariable.class);
    }

    public void deserialize(DroolsGlobalVariable element, JsonNode jsonObject, DeserializationContext context) throws IOException {
        element.setName(GenericVariableDataDeserializer.parseString("name", jsonObject));

        if (jsonObject != null && jsonObject.get("format") != null) {
            element.setFormat(DroolsGlobalVariableFormat.fromString(jsonObject.get("format").textValue()));
        }

        // Children deserialization
        final JsonNode variableDataJson = jsonObject.get("variableData");
        final List<IVariableData> children = new ArrayList<>();
        if (variableDataJson != null) {
            //Handle children one by one.
            if (variableDataJson.isArray()) {
                for (JsonNode variableData : variableDataJson) {
                    try {
                        final Class<?> classType = Class.forName(variableData.get("type").asText());
                        children.add((IVariableData) ObjectMapperFactory.getObjectMapper().readValue(variableData.toPrettyString(), classType));
                    } catch (ClassNotFoundException e) {
                        FormStructureLogger.errorMessage(this.getClass().getName(), e);
                        throw new RuntimeException(e);
                    }
                }
            }

            element.setDroolsVariableData(children);
        }
    }

    @Override
    public DroolsGlobalVariable deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final JsonNode jsonObject = jsonParser.getCodec().readTree(jsonParser);

        try {
            final DroolsGlobalVariable element = DroolsGlobalVariable.class.getDeclaredConstructor().newInstance();
            deserialize(element, jsonObject, deserializationContext);
            return element;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                 | NoSuchMethodException e) {
            FormStructureLogger.errorMessage(this.getClass().getName(), e);
            throw new RuntimeException(e);
        }
    }
}
