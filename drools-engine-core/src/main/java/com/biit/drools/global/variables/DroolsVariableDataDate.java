package com.biit.drools.global.variables;

import com.biit.drools.global.variables.exceptions.NotValidTypeInVariableData;

import java.sql.Timestamp;
import java.util.Date;

public class DroolsVariableDataDate extends DroolsVariableData {

    private Timestamp value;

    public DroolsVariableDataDate() {
    }

    @Override
    public Timestamp getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) throws NotValidTypeInVariableData {
        if (checkType(value)) {
            this.value = new Timestamp(((Date) value).getTime());
        } else {
            throw new NotValidTypeInVariableData("The type '" + value.getClass() + "' is not allowed in this variable.");
        }
    }

    public boolean checkType(Object value) {
        return value instanceof Date;
    }
}
