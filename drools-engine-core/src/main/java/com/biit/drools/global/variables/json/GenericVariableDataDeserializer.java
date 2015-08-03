package com.biit.drools.global.variables.json;

import java.lang.reflect.Type;
import java.sql.Timestamp;

import com.biit.drools.global.variables.exceptions.NotValidTypeInVariableData;
import com.biit.drools.global.variables.interfaces.IVariableData;
import com.biit.drools.logger.DroolsEngineLogger;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class GenericVariableDataDeserializer<T extends IVariableData> implements JsonDeserializer<T> {

	public void deserialize(JsonElement json, JsonDeserializationContext context, T element)
			throws NotValidTypeInVariableData {
		JsonObject jobject = (JsonObject) json;

		element.setValidFrom(parseTimestamp("validFrom", jobject, context));
		element.setValidTo(parseTimestamp("validTo", jobject, context));
	}

	public String parseString(String name, JsonObject jobject, JsonDeserializationContext context) {
		if (jobject.get(name) != null) {
			return (String) context.deserialize(jobject.get(name), String.class);
		}
		return null;
	}

	public Timestamp parseTimestamp(String name, JsonObject jobject, JsonDeserializationContext context) {
		if (jobject.get(name) != null) {
			return (Timestamp) context.deserialize(jobject.get(name), Timestamp.class);
		}
		return null;
	}

	public Double parseDouble(String name, JsonObject jobject, JsonDeserializationContext context) {
		if (jobject.get(name) != null) {
			return (Double) context.deserialize(jobject.get(name), Double.class);
		}
		return null;
	}

	public Integer parseInteger(String name, JsonObject jobject, JsonDeserializationContext context) {
		if (jobject.get(name) != null) {
			return (Integer) context.deserialize(jobject.get(name), Integer.class);
		}
		return null;
	}

	public boolean parseBoolean(String name, JsonObject jobject, JsonDeserializationContext context) {
		if (jobject.get(name) != null) {
			return (Boolean) context.deserialize(jobject.get(name), Boolean.class);
		}
		return false;
	}

	@Override
	public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

		final JsonObject jsonObject = json.getAsJsonObject();
		Class<?> classType;
		try {
			classType = Class.forName(jsonObject.get("type").getAsString());
			return context.deserialize(json, classType);
		} catch (ClassNotFoundException e) {
			DroolsEngineLogger.errorMessage(this.getClass().getName(), e);
		}

		return null;
	}

}