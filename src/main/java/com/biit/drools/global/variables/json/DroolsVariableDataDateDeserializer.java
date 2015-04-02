package com.biit.drools.global.variables.json;

import com.biit.drools.global.variables.DroolsVariableDataDate;
import com.biit.drools.global.variables.exceptions.NotValidTypeInVariableData;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DroolsVariableDataDateDeserializer extends DroolsVariableDataDeserializer<DroolsVariableDataDate> {

	public DroolsVariableDataDateDeserializer() {
		super(DroolsVariableDataDate.class);
	}

	@Override
	public void deserialize(JsonElement json, JsonDeserializationContext context, DroolsVariableDataDate element)
			throws NotValidTypeInVariableData {
		JsonObject jobject = (JsonObject) json;
		element.setValue(parseTimestamp("value", jobject, context));
		super.deserialize(json, context, element);
	}
}