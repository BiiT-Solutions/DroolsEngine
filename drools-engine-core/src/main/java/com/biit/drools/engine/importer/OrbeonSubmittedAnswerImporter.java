package com.biit.drools.engine.importer;

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
