package com.biit.drools.test;

import com.biit.drools.engine.exceptions.DroolsRuleExecutionException;
import com.biit.plugins.PluginController;
import com.biit.plugins.exceptions.DuplicatedPluginFoundException;
import com.biit.plugins.interfaces.IPlugin;
import com.biit.plugins.interfaces.exceptions.NoPluginFoundException;
import com.biit.utils.file.FileReader;
import org.dom4j.DocumentException;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

@SpringBootTest
public class PerformanceTest extends DroolsEngineFormGenerator {
	private final static String DROOLS_RULES_PATH = "rules/droolsRulesFileTest.drl";
	private final static int RULES_REPETITIONS = 100;

	private final static String HELLO_WORLD_PLUGIN_NAME = "hello-world";
	private final static String HELLO_WORLD_PLUGIN_RETURN = "Hello World";
	private final static String HELLO_WORLD_PLUGIN_METHOD = "methodHelloWorld";
	private final static String HELLO_WORLD_PLUGIN_DROOLS_FILE = "rules/helloWorldPlugin.drl";
	private final static String LIFERAY_PLUGIN_DROOLS_FILE = "rules/USMO Appendix_v1.drl";

	@Test(groups = { "performanceTest" }, enabled = true)
	public void rulesTestStaticRules() throws DroolsRuleExecutionException, FileNotFoundException, UnsupportedEncodingException, DocumentException {
		String drlFile = FileReader.getResource(DROOLS_RULES_PATH, StandardCharsets.UTF_8);
		// Execution of the rules
		long start_time = System.nanoTime();
		for (int i = 0; i < RULES_REPETITIONS; i++) {
			runDroolsRules(drlFile);
		}
		long end_time = System.nanoTime();
		// Pools (KieBuilderPool and KieFileSystemPool) improves this test from
		// 30s to 11s.
		System.out.println("############### Total Drools Execution: " + ((double) end_time - start_time) / 1e6);
	}

	@Test(groups = { "performanceTest" }, enabled = true)
	public void rulesTestPluginRules() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			FileNotFoundException, DroolsRuleExecutionException, UnsupportedEncodingException, DocumentException, NoPluginFoundException,
			DuplicatedPluginFoundException{
		// Calling the first plugin
		long start_time = System.nanoTime();
		for (int i = 0; i < RULES_REPETITIONS; i++) {
			IPlugin pluginInterface = PluginController.getInstance().getPlugin(IPlugin.class, HELLO_WORLD_PLUGIN_NAME);
			Method method = pluginInterface.getPluginMethod(HELLO_WORLD_PLUGIN_METHOD);
			Assert.assertEquals(method.invoke(pluginInterface), HELLO_WORLD_PLUGIN_RETURN);
			// Calling the hello world plugin with only one call
			Assert.assertEquals(PluginController.getInstance().executePluginMethod(IPlugin.class, HELLO_WORLD_PLUGIN_NAME, HELLO_WORLD_PLUGIN_METHOD),
					HELLO_WORLD_PLUGIN_RETURN);
			String drlFile = FileReader.getResource(HELLO_WORLD_PLUGIN_DROOLS_FILE, StandardCharsets.UTF_8);
			// Execution of the rules
			runDroolsRules(drlFile);
		}
		long end_time = System.nanoTime();
		// Pools (KieBuilderPool and KieFileSystemPool) improves this test from
		// 16s to 10s.
		System.out.println("############### Total Simple Plugin: " + ((double) end_time - start_time) / 1e6);
	}

	/**
	 * The speed of the test change depending on if the previous tests are
	 * executed or not. That means that IPluginManager and Drools caches the
	 * libraries the first time are loaded.
	 * 
	 * @throws FileNotFoundException
	 *             not desired exception.
	 * @throws DroolsRuleExecutionException
	 *             not desired exception.
	 * @throws DocumentException
	 *             not desired exception.
	 * @throws UnsupportedEncodingException
	 *             not desired exception.
	 */
	@Test(groups = { "performanceTest" }, enabled = true)
	public void rulesTestPluginRulesLiferayCall() throws FileNotFoundException, DroolsRuleExecutionException, UnsupportedEncodingException, DocumentException {
		long start_time = System.nanoTime();
		for (int i = 0; i < RULES_REPETITIONS; i++) {
			String drlFile = FileReader.getResource(LIFERAY_PLUGIN_DROOLS_FILE, StandardCharsets.UTF_8);
			// Execution of the rules
			runDroolsRules(drlFile);
		}
		long end_time = System.nanoTime();
		// Pools (KieBuilderPool and KieFileSystemPool) improves this test from
		// 23s to 15s.
		System.out.println("############### Total Liferay Plugin: " + ((double) end_time - start_time) / 1e6);
	}
}
