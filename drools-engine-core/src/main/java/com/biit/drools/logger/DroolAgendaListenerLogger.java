package com.biit.drools.logger;

import org.drools.core.event.DefaultAgendaEventListener;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;

public class DroolAgendaListenerLogger extends DefaultAgendaEventListener {

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        DroolsRulesLogger.debug(this.getClass().getName(), "Rule '{}' fired.",
                event.getMatch().getRule().getName());
    }

    @Override
    public void matchCreated(MatchCreatedEvent event) {
        DroolsRulesLogger.debug(this.getClass().getName(), "Rule '{}' created.",
                event.getMatch().getRule().getName());
    }

    @Override
    public void matchCancelled(MatchCancelledEvent event) {
        DroolsRulesLogger.debug(this.getClass().getName(), "Rule '{}' cancelled.",
                event.getMatch().getRule().getName());
    }

    @Override
    public void beforeMatchFired(BeforeMatchFiredEvent event) {
        DroolsRulesLogger.debug(this.getClass().getName(), "Rule '{}' will be fired.",
                event.getMatch().getRule().getName());
    }
}
