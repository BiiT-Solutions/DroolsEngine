package com.biit.drools.global.variables;

import com.biit.drools.global.variables.exceptions.NotValidTypeInVariableData;
import com.biit.drools.global.variables.interfaces.IVariableData;
import com.biit.drools.global.variables.serialization.DroolsVariableDataDeserializer;
import com.biit.drools.global.variables.serialization.DroolsVariableDataSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Timestamp;

@JsonDeserialize(using = DroolsVariableDataDeserializer.class)
@JsonSerialize(using = DroolsVariableDataSerializer.class)
public abstract class DroolsVariableData implements IVariableData {

    private Timestamp validFrom;

    private Timestamp validTo;

    @Override
    public Timestamp getValidFrom() {
        return validFrom;
    }

    @Override
    public void setValidFrom(Timestamp validFrom) {
        this.validFrom = validFrom;
    }

    @Override
    public Timestamp getValidTo() {
        return validTo;
    }

    @Override
    public void setValidTo(Timestamp validTo) {
        this.validTo = validTo;
    }

    @Override
    public String toString() {
        if (getValue() != null) {
            return getValue().toString();
        } else {
            return "";
        }
    }

    @Override
    public abstract Object getValue();

    @Override
    public abstract void setValue(Object value) throws NotValidTypeInVariableData;
}
