package com.biit.drools.global.variables.json;

import java.lang.reflect.Type;

import com.biit.drools.global.variables.DroolsVariableData;
import com.biit.drools.global.variables.exceptions.NotValidTypeInVariableData;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class DroolsVariableDataDeserializer<T extends DroolsVariableData> extends GenericVariableDataDeserializer<T> {

	Class<T> specificClass;

	public DroolsVariableDataDeserializer(Class<T> specificClass) {
		this.specificClass = specificClass;
	}

	@Override
	public void deserialize(JsonElement json, JsonDeserializationContext context, T element) throws NotValidTypeInVariableData {
		super.deserialize(json, context, element);
	}

	@Override
	public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		T instance;
		try {
			instance = specificClass.newInstance();
			deserialize(json, context, instance);
			return instance;
		} catch (InstantiationException | IllegalAccessException | NotValidTypeInVariableData e) {
			throw new JsonParseException(e);
		}
	}

}