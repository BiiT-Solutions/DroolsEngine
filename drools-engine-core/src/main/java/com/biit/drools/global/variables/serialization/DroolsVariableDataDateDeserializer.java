package com.biit.drools.global.variables.serialization;

import com.biit.drools.global.variables.DroolsVariableDataDate;
import com.biit.drools.global.variables.exceptions.NotValidTypeInVariableData;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class DroolsVariableDataDateDeserializer extends DroolsVariableDataDeserializer<DroolsVariableDataDate> {

    public DroolsVariableDataDateDeserializer() {
        super(DroolsVariableDataDate.class);
    }

    @Override
    public void deserialize(DroolsVariableDataDate element, JsonNode jsonObject, DeserializationContext context) throws IOException {
        try {
            element.setValue(parseTimestamp("value", jsonObject));
        } catch (NotValidTypeInVariableData e) {
            throw new IOException(e);
        }
        super.deserialize(element, jsonObject, context);
    }
}
