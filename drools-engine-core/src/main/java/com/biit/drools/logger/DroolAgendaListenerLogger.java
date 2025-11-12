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
