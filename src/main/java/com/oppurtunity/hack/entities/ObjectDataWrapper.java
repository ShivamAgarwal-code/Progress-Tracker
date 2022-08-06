package com.oppurtunity.hack.entities;

import java.util.List;

public class ObjectDataWrapper {

    private String moduleName;
    private List<ObjectDataModule> attributes;
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<ObjectDataModule> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ObjectDataModule> attributes) {
        this.attributes = attributes;
    }
}
