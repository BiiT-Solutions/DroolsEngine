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
