package com.biit.drools.global.variables.json;

/*-
 * #%L
 * Drools Engine Core
 * %%
 * Copyright (C) 2022 - 2025 BiiT Sourcing Solutions S.L.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.biit.drools.global.variables.DroolsGlobalVariable;
import com.biit.drools.global.variables.DroolsVariableData;
import com.biit.drools.global.variables.type.DroolsGlobalVariableFormat;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

/**
 * This class takes a java class and transforms it to a json string<br>
 * Used to convert the global variables array to a json string and store it
 */
public class DroolsGlobalVariableDeserializer implements JsonDeserializer<DroolsGlobalVariable> {

	@Override
	public DroolsGlobalVariable deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();
		DroolsGlobalVariable droolsGlobalVariable = new DroolsGlobalVariable(parseString("name", jsonObject, context));
		droolsGlobalVariable.setFormat(parseFormatString(parseString("format", jsonObject, context)));

		// DroolsVariableData deserialization
		Type listType = new TypeToken<ArrayList<DroolsVariableData>>() {
		}.getType();
		JsonElement valuesJson = jsonObject.get("variableData");
		if (valuesJson != null) {
			List<DroolsVariableData> values = context.deserialize(valuesJson, listType);
			droolsGlobalVariable.getGenericVariableData().addAll(values);
		}
		return droolsGlobalVariable;
	}

	public String parseString(String name, JsonObject jobject, JsonDeserializationContext context) {
		if (jobject.get(name) != null) {
			return (String) context.deserialize(jobject.get(name), String.class);
		}
		return null;
	}

	public DroolsGlobalVariableFormat parseFormatString(String format) {
		if (format.equals(DroolsGlobalVariableFormat.TEXT.toString())) {
			return DroolsGlobalVariableFormat.TEXT;
		} else if (format.equals(DroolsGlobalVariableFormat.NUMBER.toString())) {
			return DroolsGlobalVariableFormat.NUMBER;
		} else if (format.equals(DroolsGlobalVariableFormat.DATE.toString())) {
			return DroolsGlobalVariableFormat.DATE;
		} else if (format.equals(DroolsGlobalVariableFormat.POSTAL_CODE.toString())) {
			return DroolsGlobalVariableFormat.POSTAL_CODE;
		}
		return null;
	}
}
