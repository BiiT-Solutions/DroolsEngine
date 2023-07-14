package com.biit.drools.global.variables.serialization;

import com.biit.drools.global.variables.DroolsVariableDataText;
import com.biit.drools.global.variables.exceptions.NotValidTypeInVariableData;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class DroolsVariableDataTextDeserializer extends DroolsVariableDataDeserializer<DroolsVariableDataText> {

    public DroolsVariableDataTextDeserializer() {
        super(DroolsVariableDataText.class);
    }

    @Override
    public void deserialize(DroolsVariableDataText element, JsonNode jsonObject, DeserializationContext context) throws IOException {
        try {
            element.setValue(parseString("value", jsonObject));
        } catch (NotValidTypeInVariableData e) {
            throw new IOException(e);
        }
        super.deserialize(element, jsonObject, context);
    }
}
