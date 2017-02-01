package com.biit.drools.engine;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.Message.Level;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.biit.drools.engine.cache.KieBuilderPool;
import com.biit.drools.engine.cache.KieFileSystemPool;
import com.biit.drools.engine.cache.PoolableKieBuilder;
import com.biit.drools.engine.cache.PoolableKieFileSystem;
import com.biit.drools.global.variables.DroolsGlobalVariable;
import com.biit.drools.logger.DroolsEngineLogger;
import com.biit.form.submitted.ISubmittedForm;
import com.biit.logger.BiitPoolLogger;

public class KieManager {

	private List<DroolsGlobalVariable> globalVariables;
	private List<ISubmittedForm> facts;
	private KieServices kieServices;

	private static KieBuilderPool kieBuilderPool;
	private static KieFileSystemPool kieFileSystemPool;

	static {
		kieFileSystemPool = new KieFileSystemPool();
		kieBuilderPool = new KieBuilderPool();
	}

	public KieManager() {
		globalVariables = new ArrayList<DroolsGlobalVariable>();
		facts = new ArrayList<ISubmittedForm>();
	}

	public List<DroolsGlobalVariable> getGlobalVariables() {
		return globalVariables;
	}

	public void setGlobalVariables(List<DroolsGlobalVariable> globalVariables) {
		if (globalVariables != null) {
			this.globalVariables = globalVariables;
		}
	}

	public List<ISubmittedForm> getFacts() {
		return facts;
	}

	public void setFacts(List<ISubmittedForm> listOfFacts) {
		facts = listOfFacts;
	}

	public void buildSessionRules(String rules) {
		kieServices = KieServices.Factory.get();
		KieFileSystem kieFileSystem = getKieFileSystem(rules);
		build(kieServices, kieFileSystem);
	}

	public void execute() {
		startKie(getGlobalVariables(), getFacts());
	}

	/**
	 * Method in charge of initializing the kie session, set the rules,
	 * variables and facts and fire the rules
	 * 
	 * @param globalVars
	 *            variables to bused.
	 * @param facts
	 *            input values
	 */
	private void startKie(List<DroolsGlobalVariable> globalVars, List<ISubmittedForm> facts) {
		KieRepository kieRepository = kieServices.getRepository();
		KieContainer kieContainer = kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
		KieSession kieServicesession = kieContainer.newKieSession();
		setEngineGlobalVariables(kieServicesession, globalVars);
		insertFacts(kieServicesession, facts);
		kieServicesession.fireAllRules();
		kieServicesession.dispose();
	}

	private void createRules(KieFileSystem kieFileSystem, String rules) {
		// Needs a virtual path to store the file and retrieve it
		// Can't load the string directly
		kieFileSystem.write("src/main/resources/kiemodulemodel/form.drl", rules);
	}

	private void build(KieServices kieServices, KieFileSystem kieFileSystem) {
		// Build and deploy the new information.
		KieBuilder kiebuilder = getKieBuilder(kieServices, kieFileSystem);

		kiebuilder.buildAll(); // kieModule is automatically deployed to
								// KieRepository if successfully built.
		if (kiebuilder.getResults().hasMessages(Level.ERROR)) {
			throw new RuntimeException("Build Errors:\n" + kiebuilder.getResults().toString());
		}
	}

	/**
	 * These are fast operations and are not needed to cache it.
	 * 
	 * @param rules
	 * @return
	 */
	private KieFileSystem getKieFileSystem(String rules) {
		int rulesId = getRulesId(rules);
		if (kieFileSystemPool.getElement(rulesId) == null) {
			BiitPoolLogger.debug(this.getClass(), "KieFileSystem '" + rulesId + "' cache miss.");
			KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
			createRules(kieFileSystem, rules);
			PoolableKieFileSystem poolableKieFileSystem = new PoolableKieFileSystem(rulesId, kieFileSystem);
			kieFileSystemPool.addElement(poolableKieFileSystem);
			return poolableKieFileSystem.getKieFileSystem();
		} else {
			BiitPoolLogger.debug(this.getClass(), "KieFileSystem '" + rulesId + "' cache hit!");
		}
		return kieFileSystemPool.getElement(rulesId).getKieFileSystem();
	}

	private KieBuilder getKieBuilder(KieServices kieServices, KieFileSystem kieFileSystem) {
		if (kieBuilderPool.getElement(kieFileSystem) == null) {
			BiitPoolLogger.debug(this.getClass(), "KieBuilder for '" + kieFileSystem + "' cache miss.");
			PoolableKieBuilder poolableKieBuilder = new PoolableKieBuilder(kieFileSystem, kieServices.newKieBuilder(kieFileSystem));
			kieBuilderPool.addElement(poolableKieBuilder);
			return poolableKieBuilder.getKieBuilder();
		} else {
			BiitPoolLogger.debug(this.getClass(), "KieBuilder for '" + kieFileSystem + "' cache hit!");
		}
		return kieBuilderPool.getElement(kieFileSystem).getKieBuilder();
	}

	// Insert global variables in the drools session
	private void setEngineGlobalVariables(KieSession kieServicesession, List<DroolsGlobalVariable> globalVariables) {
		for (DroolsGlobalVariable droolsGlobalVariable : globalVariables) {
			DroolsEngineLogger.debug(this.getClass().getName(), "Adding global variable '" + droolsGlobalVariable.getName() + "' with value '"
					+ droolsGlobalVariable.getValue() + "'.");
			try {
				kieServicesession.setGlobal(droolsGlobalVariable.getName(), droolsGlobalVariable.getValue());
			} catch (Exception e) {
				DroolsEngineLogger.severe(this.getClass().getName(), "Global variable '" + droolsGlobalVariable.getName() + "' with value '"
						+ droolsGlobalVariable.getValue() + "' failed!");
				DroolsEngineLogger.errorMessage(this.getClass().getName(), e);
			}
		}
	}

	// Insert any number of facts in the drools session
	private void insertFacts(KieSession kieServicesession, List<ISubmittedForm> facts) {
		for (ISubmittedForm fact : facts) {
			kieServicesession.insert(fact);
		}
	};

	private int getRulesId(String rules) {
		return rules.hashCode();
	}
}
