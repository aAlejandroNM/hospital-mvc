package com.solvd.hospital.model;

import java.sql.Timestamp;

public class Patient extends Person {
    private String insuranceNumber;

    public Patient() {
    }

    public Patient(Long id, String name, int age, Timestamp createdAt, String insuranceNumber) {
        super(id, name, age, createdAt);
        this.insuranceNumber = insuranceNumber;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }
}
