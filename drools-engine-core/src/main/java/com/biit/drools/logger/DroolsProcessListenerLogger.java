package com.biit.drools.logger;

import org.drools.core.event.DebugProcessEventListener;
import org.kie.api.event.process.ProcessVariableChangedEvent;

public class DroolsProcessListenerLogger extends DebugProcessEventListener {

    @Override
    public void afterVariableChanged(ProcessVariableChangedEvent event) {
        DroolsRulesLogger.debug(this.getClass().getName(), "Variable '"
                + event.getVariableId() + "' changed from '" + event.getOldValue() + "' to '" + event.getNewValue() + "'.");
    }


}
