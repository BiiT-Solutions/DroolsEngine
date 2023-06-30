package com.biit.drools.global.variables.json;

import com.biit.drools.global.variables.DroolsVariableDataPostalCode;
import com.biit.drools.global.variables.exceptions.NotValidTypeInVariableData;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DroolsVariableDataPostalCodeDeserializer extends
        DroolsVariableDataDeserializer<DroolsVariableDataPostalCode> {

    public DroolsVariableDataPostalCodeDeserializer() {
        super(DroolsVariableDataPostalCode.class);
    }

    @Override
    public void deserialize(JsonElement json, JsonDeserializationContext context, DroolsVariableDataPostalCode element)
            throws NotValidTypeInVariableData {
        final JsonObject jobject = (JsonObject) json;
        element.setValue(parseString("value", jobject, context));
        super.deserialize(json, context, element);
    }
}
