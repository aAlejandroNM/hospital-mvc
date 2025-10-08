package com.solvd.hospital.model;

import java.sql.Timestamp;

public class Person {
    private Long id;
    private String name;
    private Integer age;
    private Timestamp createdAt;

    public Person() {
    }

    public Person(Long id, String name, int age, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
