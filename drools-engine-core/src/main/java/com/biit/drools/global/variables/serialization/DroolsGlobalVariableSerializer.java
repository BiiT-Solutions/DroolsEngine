package com.biit.drools.global.variables.serialization;

import com.biit.drools.global.variables.DroolsGlobalVariable;
import com.biit.drools.global.variables.interfaces.IVariableData;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class DroolsGlobalVariableSerializer extends StdSerializer<DroolsGlobalVariable> {

    public DroolsGlobalVariableSerializer() {
        super(DroolsGlobalVariable.class);
    }


    @Override
    public void serialize(DroolsGlobalVariable src, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        serialize(src, jgen);
        jgen.writeEndObject();
    }

    public void serialize(DroolsGlobalVariable src, JsonGenerator jgen) throws IOException {
        if (src.getName() != null) {
            jgen.writeStringField("name", src.getName());
        }
        if (src.getDroolsVariableFormat() != null) {
            jgen.writeStringField("format", src.getDroolsVariableFormat().toString());
        }

        jgen.writeFieldName("variableData");
        jgen.writeStartArray("variableData");
        for (IVariableData child : src.getGenericVariableData()) {
            jgen.writeObject(child);
        }
        jgen.writeEndArray();
    }
}
