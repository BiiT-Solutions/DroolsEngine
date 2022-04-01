package com.biit.drools.engine.debug;

import org.drools.core.spi.KnowledgeHelper;

public class Utility {
    public static void help(final KnowledgeHelper drools, final String message){
        System.out.println(message);
        System.out.println("\nRule triggered: " + drools.getRule().getName());
    }
    public static void helper(final KnowledgeHelper drools){
        System.out.println("\nRule triggered: " + drools.getRule().getName());
    }
}