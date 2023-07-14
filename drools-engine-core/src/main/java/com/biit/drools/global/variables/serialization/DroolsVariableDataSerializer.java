package com.biit.drools.global.variables.serialization;

import com.biit.drools.global.variables.DroolsVariableData;
import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;

public class DroolsVariableDataSerializer<T extends DroolsVariableData> extends GenericVariableDataSerializer<T> {

    @Override
    public void serialize(T src, JsonGenerator jgen) throws IOException {
        super.serialize(src, jgen);
    }
}
