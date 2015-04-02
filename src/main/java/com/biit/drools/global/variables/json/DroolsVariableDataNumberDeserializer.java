package com.biit.drools.global.variables.json;

import com.biit.drools.global.variables.DroolsVariableDataNumber;
import com.biit.drools.global.variables.exceptions.NotValidTypeInVariableData;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DroolsVariableDataNumberDeserializer extends DroolsVariableDataDeserializer<DroolsVariableDataNumber> {

	public DroolsVariableDataNumberDeserializer() {
		super(DroolsVariableDataNumber.class);
	}

	@Override
	public void deserialize(JsonElement json, JsonDeserializationContext context, DroolsVariableDataNumber element)
			throws NotValidTypeInVariableData {
		JsonObject jobject = (JsonObject) json;
		element.setValue(parseDouble("value", jobject, context));
		super.deserialize(json, context, element);
	}
}