package com.biit.drools.test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.biit.drools.global.variables.DroolsGlobalVariable;
import com.biit.drools.global.variables.json.DroolsGlobalVariablesFromJson;
import com.biit.utils.file.FileReader;

public class GlobalVariablesSerializerTest {

	private static final String JSON_FILE_PATH = "globalVariablesInput.json";

	@Test(groups = { "gson" })
	public void testJsonConverter() throws IOException {

		String globalVariablesJson = FileReader.getResource(JSON_FILE_PATH, StandardCharsets.UTF_8);
		List<DroolsGlobalVariable> jsonGlobalVariablesList = DroolsGlobalVariablesFromJson.fromJson(globalVariablesJson);
		Assert.assertEquals(jsonGlobalVariablesList.size(), 4);
		Assert.assertEquals(jsonGlobalVariablesList.get(2).getName(), "TestPC");
		Assert.assertEquals(jsonGlobalVariablesList.get(0).getGenericVariableData().get(1).getValue(), 21.0);
	}
}
