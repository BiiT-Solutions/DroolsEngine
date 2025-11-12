package com.biit.drools.test;

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
