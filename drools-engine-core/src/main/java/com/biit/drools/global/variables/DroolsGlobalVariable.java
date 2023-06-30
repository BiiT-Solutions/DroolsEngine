package com.biit.drools.global.variables;

import com.biit.drools.global.variables.interfaces.IGlobalVariable;
import com.biit.drools.global.variables.interfaces.IVariableData;
import com.biit.drools.global.variables.type.DroolsGlobalVariableFormat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DroolsGlobalVariable implements IGlobalVariable {

    private String name;
    private DroolsGlobalVariableFormat format;
    private List<IVariableData> droolsVariableData;

    public DroolsGlobalVariable(String name) {
        this.name = name;
    }

    public DroolsGlobalVariable(String name, List<IVariableData> droolsVariableData) {
        this.name = name;
        this.droolsVariableData = droolsVariableData;
    }

    public DroolsGlobalVariable(String name, DroolsGlobalVariableFormat format, List<IVariableData> droolsVariableData) {
        this.name = name;
        this.format = format;
        this.droolsVariableData = droolsVariableData;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object getValue() {
        // First check if the data inside the variable has a valid date
        final List<IVariableData> varDataList = getGenericVariableData();
        if ((varDataList != null) && !varDataList.isEmpty()) {
            for (IVariableData variableData : varDataList) {
                if (variableData != null) {
                    final Timestamp currentTime = new Timestamp(new Date().getTime());
                    final Timestamp initTime = variableData.getValidFrom();
                    final Timestamp endTime = variableData.getValidTo();
                    // Sometimes endtime can be null, meaning that the
                    // variable data has no ending time
                    if ((currentTime.after(initTime) && (endTime == null))
                            || (currentTime.after(initTime) && currentTime.before(endTime))) {
                        return variableData.getValue();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public DroolsGlobalVariableFormat getDroolsVariableFormat() {
        return format;
    }

    public void setFormat(DroolsGlobalVariableFormat format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return name + " (" + format + ") " + getValue();
    }

    @Override
    public List<IVariableData> getGenericVariableData() {
        if (droolsVariableData == null) {
            droolsVariableData = new ArrayList<IVariableData>();
        }
        return droolsVariableData;
    }

}
