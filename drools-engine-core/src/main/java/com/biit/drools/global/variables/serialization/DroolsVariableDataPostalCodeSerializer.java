package com.biit.drools.global.variables.serialization;

import com.biit.drools.global.variables.DroolsVariableDataPostalCode;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;

public class DroolsVariableDataPostalCodeSerializer extends DroolsVariableDataSerializer<DroolsVariableDataPostalCode> {

    @Override
    public void serialize(DroolsVariableDataPostalCode src, JsonGenerator jgen) throws IOException {
        super.serialize(src, jgen);
        if (src.getValue() != null) {
            jgen.writeStringField("value", src.getValue());
        }
    }
}
