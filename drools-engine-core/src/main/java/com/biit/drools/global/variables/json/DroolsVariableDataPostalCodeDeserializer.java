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
		JsonObject jobject = (JsonObject) json;
		element.setValue(parseString("value", jobject, context));
		super.deserialize(json, context, element);
	}
}
