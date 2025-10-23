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

import com.biit.drools.global.variables.DroolsGlobalVariable;
import com.biit.drools.global.variables.serialization.DroolsGlobalVariablesFromJson;
import com.biit.drools.global.variables.serialization.GenericVariableDataDeserializer;
import com.biit.utils.file.FileReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class GlobalVariablesSerializerTest {

    private static final String JSON_FILE_PATH = "globalVariablesInput.json";

    @Test(groups = {"jackson"})
    public void testJsonConverter() throws IOException {
        String globalVariablesJson = FileReader.getResource(JSON_FILE_PATH, StandardCharsets.UTF_8);
        List<DroolsGlobalVariable> jsonGlobalVariablesList = DroolsGlobalVariablesFromJson.fromJson(globalVariablesJson);
        Assert.assertEquals(jsonGlobalVariablesList.size(), 4);
        Assert.assertEquals(jsonGlobalVariablesList.get(2).getName(), "TestPC");
        Assert.assertEquals(jsonGlobalVariablesList.get(2).getGenericVariableData().get(0).getValidFrom(), Timestamp.valueOf(LocalDateTime.from(
                //"MMM d, yyyy h:mm:ss a"
                GenericVariableDataDeserializer.OLD_TIMESTAMP_FORMATTER.parse("Sep 23, 2007 12:00:00 AM"))));
        Assert.assertEquals(jsonGlobalVariablesList.get(0).getGenericVariableData().get(1).getValue(), 21.0);

        //Return back to json
        Assert.assertEquals(DroolsGlobalVariablesFromJson.toJson(jsonGlobalVariablesList).trim(), globalVariablesJson.trim());
    }
}
