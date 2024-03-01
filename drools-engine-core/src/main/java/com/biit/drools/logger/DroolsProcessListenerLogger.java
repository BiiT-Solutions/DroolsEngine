package com.biit.drools.logger;

import org.drools.core.event.DebugProcessEventListener;
import org.kie.api.event.process.ProcessCompletedEvent;
import org.kie.api.event.process.ProcessNodeLeftEvent;
import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.kie.api.event.process.ProcessStartedEvent;
import org.kie.api.event.process.ProcessVariableChangedEvent;

public class DroolsProcessListenerLogger extends DebugProcessEventListener {

    @Override
    public void afterVariableChanged(ProcessVariableChangedEvent event) {
        DroolsRulesLogger.debug(this.getClass().getName(), "Variable '"
                + event.getVariableId() + "' changed from '" + event.getOldValue() + "' to '" + event.getNewValue() + "'.");
    }

    public void afterNodeLeft(ProcessNodeLeftEvent event) {
        DroolsRulesLogger.debug(this.getClass().getName(), "Node '"
                + event.getNodeInstance().getNodeName() + "' has left.");
    }

    public void afterNodeTriggered(ProcessNodeTriggeredEvent event) {
        DroolsRulesLogger.debug(this.getClass().getName(), "Node '"
                + event.getNodeInstance().getNodeName() + "' has triggered.");
    }

    public void afterProcessCompleted(ProcessCompletedEvent event) {
        DroolsRulesLogger.debug(this.getClass().getName(), "### Process '"
                + event.getProcessInstance().getProcessId() + "' has been completed ###");
    }

    public void afterProcessStarted(ProcessStartedEvent event) {
        DroolsRulesLogger.debug(this.getClass().getName(), "### Process '"
                + event.getProcessInstance().getProcessId() + "' has been started ###");
    }

    public void beforeNodeLeft(ProcessNodeLeftEvent event) {
        DroolsRulesLogger.debug(this.getClass().getName(), "Node '"
                + event.getNodeInstance().getNodeName() + "' will left.");
    }

    public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
        DroolsRulesLogger.debug(this.getClass().getName(), "Node '"
                + event.getNodeInstance().getNodeName() + "' will be triggered.");
    }

    public void beforeProcessCompleted(ProcessCompletedEvent event) {
        DroolsRulesLogger.debug(this.getClass().getName(), "Process '"
                + event.getProcessInstance().getProcessId() + "' will finish.");
    }

    public void beforeProcessStarted(ProcessStartedEvent event) {
        DroolsRulesLogger.debug(this.getClass().getName(), "Process '"
                + event.getProcessInstance().getProcessId() + "' is going to start");
    }

    public void beforeVariableChanged(ProcessVariableChangedEvent event) {
        DroolsRulesLogger.debug(this.getClass().getName(), "Variable '"
                + event.getVariableId() + "' is going to change '" + event.getOldValue() + "' to '" + event.getNewValue() + "'.");
    }

}
