package com.biit.drools.global.variables;

import com.biit.drools.global.variables.exceptions.NotValidTypeInVariableData;

public class DroolsVariableDataNumber extends DroolsVariableData {
    private Double value;

    public DroolsVariableDataNumber() {
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) throws NotValidTypeInVariableData {
        if (!checkType(value)) {
            throw new NotValidTypeInVariableData("The type '" + value.getClass() + "' is not allowed in this variable.");
        }
    }

    public boolean checkType(Object value) {
        Double aux = null;
        if (value instanceof String) {
            try {
                aux = Double.parseDouble((String) value);
            } catch (Exception e) {
            }
        } else {
            aux = (Double) value;
        }

        if (aux != null) {
            this.value = aux;
            return true;
        } else {
            return false;
        }
    }
}
