package com.biit.drools.engine.importer;

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

import com.biit.drools.form.DroolsSubmittedCategory;
import com.biit.drools.form.DroolsSubmittedForm;
import com.biit.drools.form.DroolsSubmittedGroup;
import com.biit.drools.form.DroolsSubmittedQuestion;
import com.biit.form.submitted.ISubmittedCategory;
import com.biit.form.submitted.ISubmittedForm;
import com.biit.form.submitted.ISubmittedGroup;
import com.biit.form.submitted.ISubmittedObject;
import com.biit.form.submitted.ISubmittedQuestion;
import com.biit.form.submitted.implementation.SubmittedObject;
import com.biit.orbeon.OrbeonImporter;

/**
 * Reads data from Orbeon Form.
 */
public class OrbeonSubmittedAnswerImporter extends OrbeonImporter {

    @Override
    public ISubmittedCategory createCategory(ISubmittedObject parent, String tag) {
        final ISubmittedCategory category = new DroolsSubmittedCategory(tag);
        category.setParent((SubmittedObject) parent);
        return category;
    }

    @Override
    public ISubmittedForm createForm(String applicationName, String formName) {
        return new DroolsSubmittedForm(applicationName, formName);
    }

    @Override
    public ISubmittedGroup createGroup(ISubmittedObject parent, String tag) {
        final ISubmittedGroup group = new DroolsSubmittedGroup(tag);
        group.setParent((SubmittedObject) parent);
        return group;
    }

    @Override
    public ISubmittedQuestion createQuestion(ISubmittedObject parent, String tag) {
        final ISubmittedQuestion question = new DroolsSubmittedQuestion(tag);
        question.setParent((SubmittedObject) parent);
        return question;
    }
}
