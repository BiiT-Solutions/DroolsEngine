package com.biit.drools.logger;

import org.drools.core.event.DefaultAgendaEventListener;
import org.kie.api.definition.rule.Rule;
import org.kie.api.event.rule.AfterMatchFiredEvent;

public class DroolRuleListenerLogger extends DefaultAgendaEventListener {

	@Override
	public void afterMatchFired(AfterMatchFiredEvent event) {
		Rule rule = event.getMatch().getRule();
		DroolsEngineLogger.debug(this.getClass().getName(), "Rule fired '" + rule.getName() + "'.");
	}
}