package com.biit.drools.global.variables.serialization;

import com.biit.drools.global.variables.DroolsVariableDataText;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;

public class DroolsVariableDataTextSerializer extends DroolsVariableDataSerializer<DroolsVariableDataText> {

    @Override
    public void serialize(DroolsVariableDataText src, JsonGenerator jgen) throws IOException {
        super.serialize(src, jgen);
        if (src.getValue() != null) {
            jgen.writeStringField("value", src.getValue());
        }
    }
}
