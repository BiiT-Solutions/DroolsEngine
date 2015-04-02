package com.biit.drools;

import com.biit.form.BaseForm;

public class DroolsHelper {

	private BaseForm form;

	public DroolsHelper(BaseForm form){
		setForm(form);
	}
	
	public BaseForm getForm() {
		return form;
	}

	public void setForm(BaseForm form) {
		this.form = form;
	}
}
