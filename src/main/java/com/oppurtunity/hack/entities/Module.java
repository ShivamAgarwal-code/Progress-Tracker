package com.oppurtunity.hack.entities;

public class Module {

    private String id;
    private String label;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Module{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
