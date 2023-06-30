package com.biit.drools.global.variables;

import com.biit.drools.global.variables.exceptions.NotValidTypeInVariableData;

public class DroolsVariableDataText extends DroolsVariableData {

    private String value;

    public DroolsVariableDataText() {
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) throws NotValidTypeInVariableData {
        if (checkType(value)) {
            this.value = (String) value;
        } else {
            throw new NotValidTypeInVariableData("The type '" + value.getClass() + "' is not allowed in this variable.");
        }
    }

    public boolean checkType(Object value) {
        return value instanceof String;
    }
}
