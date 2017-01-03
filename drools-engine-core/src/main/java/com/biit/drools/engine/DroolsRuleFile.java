package com.biit.drools.engine;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

import com.biit.utils.file.FileReader;

public class DroolsRuleFile {

	private String resourceName;

	private transient String content;

	public DroolsRuleFile(String resourceName) throws FileNotFoundException {
		this.resourceName = resourceName;
		content = FileReader.getResource(resourceName, StandardCharsets.UTF_8);
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getContent() {
		return content;
	}

}
