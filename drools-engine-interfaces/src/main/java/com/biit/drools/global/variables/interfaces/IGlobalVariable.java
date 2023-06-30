package com.biit.drools.global.variables.interfaces;

import com.biit.drools.global.variables.type.DroolsGlobalVariableFormat;

import java.util.List;

public interface IGlobalVariable {

    String getName();

    DroolsGlobalVariableFormat getDroolsVariableFormat();

    Object getValue();

    List<IVariableData> getGenericVariableData();
}
