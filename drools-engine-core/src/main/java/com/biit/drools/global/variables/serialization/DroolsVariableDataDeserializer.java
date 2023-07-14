package com.biit.drools.global.variables.serialization;

import com.biit.drools.global.variables.DroolsVariableData;
import com.biit.form.log.FormStructureLogger;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class DroolsVariableDataDeserializer<T extends DroolsVariableData> extends GenericVariableDataDeserializer<T> {

    private final Class<T> specificClass;

    public DroolsVariableDataDeserializer(Class<T> specificClass) {
        this.specificClass = specificClass;
    }

    @Override
    public void deserialize(T element, JsonNode jsonObject, DeserializationContext context) throws IOException {
        super.deserialize(element, jsonObject, context);
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final JsonNode jsonObject = jsonParser.getCodec().readTree(jsonParser);

        try {
            final T element = specificClass.getDeclaredConstructor().newInstance();
            deserialize(element, jsonObject, deserializationContext);
            return element;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                 | NoSuchMethodException e) {
            FormStructureLogger.errorMessage(this.getClass().getName(), e);
            throw new RuntimeException(e);
        }
    }
}
