package com.biit.drools.global.variables.interfaces;

import java.sql.Timestamp;

import com.biit.drools.global.variables.exceptions.NotValidTypeInVariableData;


public interface IVariableData {

	public Object getValue();

	public void setValue(Object value) throws NotValidTypeInVariableData;

	public Timestamp getValidFrom();

	public void setValidFrom(Timestamp validFrom);

	public Timestamp getValidTo();

	public void setValidTo(Timestamp validTo);

	public String toString();
}
