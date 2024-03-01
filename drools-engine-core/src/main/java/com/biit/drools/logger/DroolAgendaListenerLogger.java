package com.biit.drools.logger;

import org.drools.core.event.DefaultAgendaEventListener;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.MatchCancelledEvent;
import org.kie.api.event.rule.MatchCreatedEvent;

public class DroolAgendaListenerLogger extends DefaultAgendaEventListener {

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        DroolsRulesLogger.debug(this.getClass().getName(), "Rule '{}' fired with salience '{}'.",
                event.getMatch().getRule().getName(), event.getMatch().getSalience());
    }

    @Override
    public void matchCreated(MatchCreatedEvent event) {
        DroolsRulesLogger.debug(this.getClass().getName(), "Rule '{}' with salience '{}' created.",
                event.getMatch().getRule().getName(), event.getMatch().getSalience());
    }

    @Override
    public void matchCancelled(MatchCancelledEvent event) {
        DroolsRulesLogger.debug(this.getClass().getName(), "Rule '{}' with salience '{}' cancelled.",
                event.getMatch().getRule().getName(), event.getMatch().getSalience());
    }

    @Override
    public void beforeMatchFired(BeforeMatchFiredEvent event) {
        DroolsRulesLogger.debug(this.getClass().getName(), "Rule '{}' with salience '{}' will be fired.",
                event.getMatch().getRule().getName(), event.getMatch().getSalience());
    }
}

