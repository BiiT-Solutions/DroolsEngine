package com.biit.drools.engine;

import com.biit.drools.global.variables.interfaces.IGlobalVariable;
import com.biit.drools.logger.DebugRuleListenerLogger;
import com.biit.drools.logger.DroolAgendaListenerLogger;
import com.biit.drools.logger.DroolsEngineLogger;
import com.biit.drools.logger.DroolsProcessListenerLogger;
import com.biit.form.submitted.ISubmittedForm;
import com.biit.persistence.utils.IdGenerator;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.Message.Level;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class KieManager {

    private List<IGlobalVariable> globalVariables;
    private List<ISubmittedForm> facts;
    private KieServices kieServices;

    public KieManager() {
        globalVariables = new ArrayList<>();
        facts = new ArrayList<>();
    }

    public List<IGlobalVariable> getGlobalVariables() {
        return globalVariables;
    }

    public void setGlobalVariables(List<IGlobalVariable> globalVariables) {
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
        if (rules != null) {
            kieServices = KieServices.Factory.get();
            final int rulesId = getRulesId(rules);
            final KieFileSystem kieFileSystem = getKieFileSystem(rules, rulesId);
            build(kieServices, kieFileSystem, rulesId);
        }
    }

    public void execute() {
        startKie(getGlobalVariables(), getFacts());
    }

    /**
     * Method in charge of initializing the kie session, set the rules, variables
     * and facts and fire the rules
     *
     * @param globalVars variables to bused.
     * @param facts      input values
     */
    private void startKie(List<IGlobalVariable> globalVars, List<ISubmittedForm> facts) {
        final KieRepository kieRepository = kieServices.getRepository();
        final KieContainer kieContainer = kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
        final KieSession kieServicesession = kieContainer.newKieSession();
        setEngineGlobalVariables(kieServicesession, globalVars);
        insertFacts(kieServicesession, facts);
        kieServicesession.addEventListener(new DroolAgendaListenerLogger());
        kieServicesession.addEventListener(new DroolsProcessListenerLogger());
        kieServicesession.addEventListener(new DebugRuleListenerLogger());
        KieRuntimeLogger kieLogger = null;
        try {
            if (DroolsEngineLogger.isDebugEnabled()) {
                kieLogger = kieServices.getLoggers().newFileLogger(kieServicesession,
                        Files.createTempFile("DroolsAudit", ".log").toString(), 0);
            }
        } catch (IOException e) {
            DroolsEngineLogger.errorMessage(this.getClass().getName(), e);
        }
        kieServicesession.fireAllRules();
        kieServicesession.dispose();
        if (kieLogger != null) {
            kieLogger.close();
        }
    }

    private void createRules(KieFileSystem kieFileSystem, String rules) {
        // Needs a virtual path to store the file and retrieve it
        // Can't load the string directly
        kieFileSystem.write("src/main/resources/kiemodulemodel/form_" + IdGenerator.createId() + ".drl", rules);
    }

    private void build(KieServices kieServices, KieFileSystem kieFileSystem, int rulesId) {
        // Build and deploy the new information.
        final KieBuilder kiebuilder = getKieBuilder(kieServices, kieFileSystem, rulesId);

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
    private KieFileSystem getKieFileSystem(String rules, int id) {
        final KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        createRules(kieFileSystem, rules);
        return kieFileSystem;
    }

    private KieBuilder getKieBuilder(KieServices kieServices, KieFileSystem kieFileSystem, int id) {
        return kieServices.newKieBuilder(kieFileSystem);
    }

    // Insert global variables in the drools session
    private void setEngineGlobalVariables(KieSession kieServiceSession, List<IGlobalVariable> globalVariables) {
        for (IGlobalVariable droolsGlobalVariable : globalVariables) {
            DroolsEngineLogger.debug(this.getClass().getName(), "Adding global variable '"
                    + droolsGlobalVariable.getName() + "' with value '" + droolsGlobalVariable.getValue() + "'.");
            try {
                kieServiceSession.setGlobal(droolsGlobalVariable.getName(), droolsGlobalVariable.getValue());
            } catch (Exception e) {
                DroolsEngineLogger.severe(this.getClass().getName(),
                        "Global variable '" + droolsGlobalVariable.getName() + "' with value '"
                                + droolsGlobalVariable.getValue() + "' failed!");
                DroolsEngineLogger.errorMessage(this.getClass().getName(), e);
            }
        }
    }

    // Insert any number of facts in the drools session
    private void insertFacts(KieSession kieServiceSession, List<ISubmittedForm> facts) {
        for (ISubmittedForm fact : facts) {
            kieServiceSession.insert(fact);
        }
    }

    private int getRulesId(String rules) {
        return rules.hashCode();
    }
}
