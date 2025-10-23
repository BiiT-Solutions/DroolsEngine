package com.biit.drools.global.variables.serialization;

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

import com.biit.drools.global.variables.DroolsGlobalVariable;
import com.biit.form.jackson.serialization.ObjectMapperFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

/**
 * This class takes a java class and transforms it to a json string<br>
 * Used to convert the global variables array to a json string and store it
 */
public final class DroolsGlobalVariablesFromJson {

    private DroolsGlobalVariablesFromJson() {

    }

    public static List<DroolsGlobalVariable> fromJson(String jsonString) {
        try {
            return ObjectMapperFactory.getObjectMapper().readValue(jsonString, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJson(List<DroolsGlobalVariable> droolsGlobalVariables) {
        try {
            return ObjectMapperFactory.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(droolsGlobalVariables);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
