package com.biit.drools.global.variables.interfaces;

import java.util.List;

import com.biit.drools.global.variables.type.DroolsGlobalVariableFormat;

public interface IGlobalVariable {

	public String getName();

	public DroolsGlobalVariableFormat getDroolsVariableFormat();

	public Object getValue();
	
	public List<IVariableData> getGenericVariableData();
}
