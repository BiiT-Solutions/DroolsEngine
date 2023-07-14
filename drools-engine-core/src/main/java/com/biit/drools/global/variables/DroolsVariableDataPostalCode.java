package com.biit.drools.global.variables;


import com.biit.drools.global.variables.serialization.DroolsVariableDataPostalCodeDeserializer;
import com.biit.drools.global.variables.serialization.DroolsVariableDataPostalCodeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonDeserialize(using = DroolsVariableDataPostalCodeDeserializer.class)
@JsonSerialize(using = DroolsVariableDataPostalCodeSerializer.class)
public class DroolsVariableDataPostalCode extends DroolsVariableDataText {

    public DroolsVariableDataPostalCode() {
    }
}
