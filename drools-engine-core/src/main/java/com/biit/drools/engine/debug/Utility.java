package com.biit.drools.engine.debug;

import org.drools.core.rule.consequence.KnowledgeHelper;

public final class Utility {

    private Utility() {

    }

    public static void help(final KnowledgeHelper drools, final String message) {
        System.out.println(message);
        System.out.println("\nRule triggered: " + drools.getRule().getName());
    }

    public static void helper(final KnowledgeHelper drools) {
        System.out.println("\nRule triggered: " + drools.getRule().getName());
    }
}
