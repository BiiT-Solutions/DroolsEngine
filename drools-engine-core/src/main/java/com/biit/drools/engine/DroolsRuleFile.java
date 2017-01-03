package com.biit.drools.engine;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

import com.biit.utils.file.FileReader;

public class DroolsRuleFile {

	private String resourceName;

	private String content;

	public DroolsRuleFile(String resourceName) throws FileNotFoundException {
		this.resourceName = resourceName;
		content = FileReader.getResource(resourceName, StandardCharsets.UTF_8);
	}

	public DroolsRuleFile() {
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
