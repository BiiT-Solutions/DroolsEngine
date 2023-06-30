package com.biit.drools.global.variables.interfaces;

import com.biit.drools.global.variables.exceptions.NotValidTypeInVariableData;

import java.sql.Timestamp;

public interface IVariableData {

    Object getValue();

    void setValue(Object value) throws NotValidTypeInVariableData;

    Timestamp getValidFrom();

    void setValidFrom(Timestamp validFrom);

    Timestamp getValidTo();

    void setValidTo(Timestamp validTo);

    String toString();
}
