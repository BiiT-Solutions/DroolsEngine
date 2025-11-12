package com.biit.drools.engine;

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

import java.util.Arrays;
import java.util.List;

import com.biit.drools.global.variables.interfaces.IGlobalVariable;
import org.springframework.stereotype.Component;

import com.biit.drools.engine.exceptions.DroolsRuleExecutionException;
import com.biit.drools.form.DroolsForm;
import com.biit.drools.global.variables.DroolsGlobalVariable;
import com.biit.drools.logger.DroolsEngineLogger;
import com.biit.drools.logger.DroolsRulesLogger;
import com.biit.form.submitted.ISubmittedForm;
import com.biit.form.submitted.implementation.SubmittedForm;

@Component
public class DroolsRulesEngine {

	/**
	 * Method for executing the drools rules
	 *
	 * @param submittedForm   without scores
	 * @param droolsRules     rules to be applied
	 * @param globalVariables variables to be used.
	 * @return submittedForm with the scores calculated by drools
	 * @throws DroolsRuleExecutionException something was wrong.
	 */
	public DroolsForm applyDrools(ISubmittedForm submittedForm, String droolsRules,
			List<IGlobalVariable> globalVariables) throws DroolsRuleExecutionException {
		DroolsForm droolsForm = null;
		try {
			if (droolsRules != null && droolsRules.length() > 0) {
				DroolsRulesLogger.debug(this.getClass().getName(), "Rules launched:\n" + droolsRules);
				// Launch kie
				KieManager km = new KieManager();
				// Load the rules in memory. Takes around the 60% of the
				// operation time.
				km.buildSessionRules(droolsRules);
				if (globalVariables != null && !globalVariables.isEmpty()) {
					// Creation of the global constants
					km.setGlobalVariables(globalVariables);
				}
				droolsForm = new DroolsForm(submittedForm);
				// Takes 25% of the operation time.
				runDroolsRules(droolsForm, km);
			}
		} catch (Exception e) {
			DroolsEngineLogger.errorMessage(this.getClass().getName(), e);
			throw new DroolsRuleExecutionException("Error executing the drools rules", e);
		}
		return droolsForm;
	}

	/**
	 * Loads the (Submitted) form as facts of the knowledge base of the drools
	 * engine. <br>
	 * It also starts the engine execution by firing all the rules inside the
	 * engine.
	 *
	 * @param form       form with the values. Also has the drools returned results.
	 * @param kieManager manager to be used.
	 */
	private void runDroolsRules(ISubmittedForm form, KieManager kieManager) {
		if ((form != null) && (kieManager != null)) {
			kieManager.setFacts(Arrays.asList(form));
			kieManager.execute();
		}
	}
}
