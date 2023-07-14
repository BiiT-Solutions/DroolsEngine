package com.biit.drools.global.variables.serialization;

import com.biit.drools.global.variables.DroolsVariableDataPostalCode;
import com.biit.drools.global.variables.exceptions.NotValidTypeInVariableData;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class DroolsVariableDataPostalCodeDeserializer extends DroolsVariableDataDeserializer<DroolsVariableDataPostalCode> {

    public DroolsVariableDataPostalCodeDeserializer() {
        super(DroolsVariableDataPostalCode.class);
    }

    @Override
    public void deserialize(DroolsVariableDataPostalCode element, JsonNode jsonObject, DeserializationContext context) throws IOException {
        try {
            element.setValue(parseString("value", jsonObject));
        } catch (NotValidTypeInVariableData e) {
            throw new IOException(e);
        }
        super.deserialize(element, jsonObject, context);
    }
}
