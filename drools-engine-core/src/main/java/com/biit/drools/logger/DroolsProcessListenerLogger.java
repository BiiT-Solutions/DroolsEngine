package com.biit.drools.logger;

/*-
 * #%L
 * Drools Engine Core
 * %%
 * Copyright (C) 2022 - 2025 BiiT Sourcing Solutions S.L.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

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
