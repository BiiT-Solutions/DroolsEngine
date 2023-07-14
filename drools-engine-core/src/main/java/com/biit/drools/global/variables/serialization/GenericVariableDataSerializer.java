package com.biit.drools.global.variables.serialization;

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
