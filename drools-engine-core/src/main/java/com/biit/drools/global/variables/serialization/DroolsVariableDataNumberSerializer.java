package com.biit.drools.global.variables.serialization;

import com.biit.drools.global.variables.DroolsVariableDataNumber;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;

public class DroolsVariableDataNumberSerializer extends DroolsVariableDataSerializer<DroolsVariableDataNumber> {

    @Override
    public void serialize(DroolsVariableDataNumber src, JsonGenerator jgen) throws IOException {
        super.serialize(src, jgen);
        if (src.getValue() != null) {
            jgen.writeNumberField("value", src.getValue());
        }
    }
}
