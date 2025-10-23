package com.biit.drools.global.variables;

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

import com.biit.drools.global.variables.exceptions.NotValidTypeInVariableData;
import com.biit.drools.global.variables.interfaces.IVariableData;
import com.biit.drools.global.variables.serialization.DroolsVariableDataDeserializer;
import com.biit.drools.global.variables.serialization.DroolsVariableDataSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Timestamp;

@JsonDeserialize(using = DroolsVariableDataDeserializer.class)
@JsonSerialize(using = DroolsVariableDataSerializer.class)
public abstract class DroolsVariableData implements IVariableData {

    private Timestamp validFrom;

    private Timestamp validTo;

    @Override
    public Timestamp getValidFrom() {
        return validFrom;
    }

    @Override
    public void setValidFrom(Timestamp validFrom) {
        this.validFrom = validFrom;
    }

    @Override
    public Timestamp getValidTo() {
        return validTo;
    }

    @Override
    public void setValidTo(Timestamp validTo) {
        this.validTo = validTo;
    }

    @Override
    public String toString() {
        if (getValue() != null) {
            return getValue().toString();
        } else {
            return "";
        }
    }

    @Override
    public abstract Object getValue();

    @Override
    public abstract void setValue(Object value) throws NotValidTypeInVariableData;
}
