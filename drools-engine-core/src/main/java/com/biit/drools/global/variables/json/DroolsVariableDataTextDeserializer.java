package com.biit.drools.global.variables.json;

import com.biit.drools.global.variables.DroolsVariableDataText;
import com.biit.drools.global.variables.exceptions.NotValidTypeInVariableData;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DroolsVariableDataTextDeserializer extends DroolsVariableDataDeserializer<DroolsVariableDataText> {

    public DroolsVariableDataTextDeserializer() {
        super(DroolsVariableDataText.class);
    }

    @Override
    public void deserialize(JsonElement json, JsonDeserializationContext context, DroolsVariableDataText element)
            throws NotValidTypeInVariableData {
        final JsonObject jobject = (JsonObject) json;
        element.setValue(parseString("value", jobject, context));
        super.deserialize(json, context, element);
    }
}
