package com.biit.drools.global.variables.serialization;

import com.biit.drools.global.variables.DroolsVariableDataDate;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;

public class DroolsVariableDataDateSerializer extends DroolsVariableDataSerializer<DroolsVariableDataDate> {

    @Override
    public void serialize(DroolsVariableDataDate src, JsonGenerator jgen) throws IOException {
        super.serialize(src, jgen);
        if (src.getValue() != null) {
            jgen.writeStringField("value", GenericVariableDataDeserializer.TIMESTAMP_FORMATTER.format(src.getValue().toLocalDateTime()));
        }
    }
}
