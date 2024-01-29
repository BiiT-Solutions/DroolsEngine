package com.biit.drools.logger;

import org.drools.core.event.DebugRuleRuntimeEventListener;
import org.kie.api.event.rule.ObjectInsertedEvent;

public class DebugRuleListenerLogger extends DebugRuleRuntimeEventListener {

    @Override
    public void objectInserted(ObjectInsertedEvent event) {
        DroolsRulesLogger.debug(this.getClass().getName(), "Rule '" + event.getRule().getName() + "' inserted.");
    }
}
