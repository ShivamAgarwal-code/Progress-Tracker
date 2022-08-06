package com.oppurtunity.hack.entities;

import java.util.List;

public class EventWrapper {
	private String moduleName;
	private String objectName;
	private List<Module> attributes;

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public List<Module> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Module> attributes) {
		this.attributes = attributes;
	}
}
