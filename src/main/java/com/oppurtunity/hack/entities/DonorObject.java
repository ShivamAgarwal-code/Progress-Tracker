package com.oppurtunity.hack.entities;

import java.io.Serializable;

public class DonorObject implements Serializable {

    private String name;

    private String email;

    private String age;

    private String city;

    public DonorObject(String name, String email, String age, String city) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.city = city;
    }

    @Override
    public String toString() {
        return "DonorObject{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age='" + age + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
