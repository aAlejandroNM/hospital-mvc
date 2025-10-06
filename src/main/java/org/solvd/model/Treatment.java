package org.solvd.model;

import java.sql.Timestamp;

public class Treatment {
    private Long id;
    private Patient patient;
    private Disease disease;
    private String name;
    private Timestamp createdAt;

    public Treatment() {
    }

    public Treatment(Long id, Patient patient, Disease disease, String name, Timestamp createdAt) {
        this.id = id;
        this.patient = patient;
        this.disease = disease;
        this.name = name;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
