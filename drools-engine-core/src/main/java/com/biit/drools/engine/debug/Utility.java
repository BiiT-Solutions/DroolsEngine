package com.biit.drools.engine.debug;

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
