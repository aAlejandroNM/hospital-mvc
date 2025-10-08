package com.solvd.hospital.model;

import java.sql.Timestamp;

public class MedicalRecord extends MedicalDocument {
    private Patient patient;
    private Disease disease;
    private Staff createdBy;

    public MedicalRecord() {
    }

    public MedicalRecord(Long id, String documentType, Timestamp creationDate, Patient patient, Disease disease, Staff createdBy) {
        super(id, documentType, creationDate);
        this.patient = patient;
        this.disease = disease;
        this.createdBy = createdBy;
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

    public Staff getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Staff createdBy) {
        this.createdBy = createdBy;
    }
}
