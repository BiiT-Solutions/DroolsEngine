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
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class GenericVariableDataSerializer<T extends IVariableData> extends StdSerializer<T> {

    public GenericVariableDataSerializer() {
        this(null);
    }

    public GenericVariableDataSerializer(Class<T> t) {
        super(t);
    }

    @Override
    public void serialize(T src, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        serialize(src, jgen);
        jgen.writeEndObject();
    }

    public void serialize(T src, JsonGenerator jgen) throws IOException {
        jgen.writeStringField("type", src.getClass().getName());
        if (src.getValidFrom() != null) {
            jgen.writeStringField("validFrom", GenericVariableDataDeserializer.TIMESTAMP_FORMATTER.format(src.getValidFrom().toLocalDateTime()));
        }
        if (src.getValidTo() != null) {
            jgen.writeStringField("validTo", GenericVariableDataDeserializer.TIMESTAMP_FORMATTER.format(src.getValidTo().toLocalDateTime()));
        }
    }
}
