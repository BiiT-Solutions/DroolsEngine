package com.biit.drools.global.variables.type;

public enum DroolsGlobalVariableFormat {

    TEXT,

    NUMBER,

    DATE,

    POSTAL_CODE,

    MULTI_TEXT;

    public static DroolsGlobalVariableFormat fromString(String tag) {
        for (DroolsGlobalVariableFormat format : DroolsGlobalVariableFormat.values()) {
            if (format.name().equalsIgnoreCase(tag)) {
                return format;
            }
        }
        return null;
    }
}
