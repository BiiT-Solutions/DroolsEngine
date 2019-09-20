package com.biit.drools.engine;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.biit.drools.engine.exceptions.DroolsRuleExecutionException;
import com.biit.drools.form.DroolsForm;
import com.biit.drools.global.variables.DroolsGlobalVariable;
import com.biit.drools.logger.DroolsEngineLogger;
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
			List<DroolsGlobalVariable> globalVariables) throws DroolsRuleExecutionException {
		DroolsForm droolsForm = null;
		try {
			if (droolsRules != null && droolsRules.length() > 0) {
				DroolsEngineLogger.debug(this.getClass().getName(), "Rules launched:\n" + droolsRules);
				// Launch kie
				KieManager km = new KieManager();
				// Load the rules in memory. Takes around the 60% of the
				// operation time.
				km.buildSessionRules(droolsRules);
				if (globalVariables != null && !globalVariables.isEmpty()) {
					// Creation of the global constants
					km.setGlobalVariables(globalVariables);
				}
				droolsForm = new DroolsForm((SubmittedForm) submittedForm);
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
