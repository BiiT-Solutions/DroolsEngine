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

import com.biit.drools.global.variables.interfaces.IVariableData;
import com.biit.form.log.FormStructureLogger;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class GenericVariableDataDeserializer<T extends IVariableData> extends StdDeserializer<T> {
    protected static final String TIMESTAMP_FORMAT = "dd-MM-yyyy HH:mm:ss";
    protected static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT);
    private static final String OLD_TIMESTAMP_FORMAT = "MMM d, yyyy h:mm:ss a";
    public static final DateTimeFormatter OLD_TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern(OLD_TIMESTAMP_FORMAT, Locale.ENGLISH);

    private final Class<T> specificClass;

    public GenericVariableDataDeserializer() {
        this(null);
    }

    public GenericVariableDataDeserializer(Class<T> aClass) {
        super(aClass);
        this.specificClass = aClass;
    }

    public void deserialize(T element, JsonNode jsonObject, DeserializationContext context) throws IOException {
        element.setValidFrom(parseTimestamp("validFrom", jsonObject));
        element.setValidTo(parseTimestamp("validTo", jsonObject));
    }

    protected static String parseString(String name, JsonNode jsonObject) {
        if (jsonObject != null && jsonObject.get(name) != null) {
            return jsonObject.get(name).textValue();
        }
        return null;
    }

    protected static Timestamp parseTimestamp(String name, JsonNode jsonObject) {
        if (jsonObject != null && jsonObject.get(name) != null) {
            final String value = jsonObject.get(name).asText();
            try {
                return Timestamp.valueOf(value);
            } catch (Exception e) {
                try {
                    return Timestamp.valueOf(LocalDateTime.from(OLD_TIMESTAMP_FORMATTER.parse(value)));
                } catch (Exception e2) {
                    try {
                        return Timestamp.valueOf(LocalDateTime.from(TIMESTAMP_FORMATTER.parse(value)));
                    } catch (Exception e3) {
                        return new Timestamp(new Date().getTime());
                    }
                }
            }
        }
        return null;
    }

    protected static Long parseLong(String name, JsonNode jsonObject) {
        if (jsonObject != null && jsonObject.get(name) != null) {
            return jsonObject.get(name).longValue();
        }
        return null;
    }

    protected static Double parseDouble(String name, JsonNode jsonObject) {
        if (jsonObject != null && jsonObject.get(name) != null) {
            return jsonObject.get(name).doubleValue();
        }
        return null;
    }

    protected static Integer parseInteger(String name, JsonNode jsonObject) {
        if (jsonObject != null && jsonObject.get(name) != null) {
            return jsonObject.get(name).intValue();
        }
        return null;
    }

    protected boolean parseBoolean(String name, JsonNode jsonObject) {
        if (jsonObject != null && jsonObject.get(name) != null) {
            return jsonObject.get(name).booleanValue();
        }
        return false;
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final JsonNode jsonObject = jsonParser.getCodec().readTree(jsonParser);

        final Class<T> classType;
        try {
            classType = (Class<T>) Class.forName(jsonObject.get("type").asText());
            final T element = classType.getDeclaredConstructor().newInstance();
            deserialize(element, jsonObject, deserializationContext);
            return element;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException
                 | NoSuchMethodException e) {
            FormStructureLogger.errorMessage(this.getClass().getName(), e);
            throw new RuntimeException(e);
        }
    }
}
