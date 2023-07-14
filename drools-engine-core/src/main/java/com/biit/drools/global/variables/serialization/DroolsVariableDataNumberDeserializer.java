package com.biit.drools.global.variables.serialization;

import com.biit.drools.global.variables.DroolsVariableDataNumber;
import com.biit.drools.global.variables.exceptions.NotValidTypeInVariableData;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class DroolsVariableDataNumberDeserializer extends DroolsVariableDataDeserializer<DroolsVariableDataNumber> {

    public DroolsVariableDataNumberDeserializer() {
        super(DroolsVariableDataNumber.class);
    }

    @Override
    public void deserialize(DroolsVariableDataNumber element, JsonNode jsonObject, DeserializationContext context) throws IOException {
        try {
            element.setValue(parseDouble("value", jsonObject));
        } catch (NotValidTypeInVariableData e) {
            throw new IOException(e);
        }
        super.deserialize(element, jsonObject, context);
    }
}
