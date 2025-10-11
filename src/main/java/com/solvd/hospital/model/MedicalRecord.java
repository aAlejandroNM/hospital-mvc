package com.solvd.hospital.model;

import jakarta.xml.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@XmlRootElement(name = "medicalRecord")
@XmlAccessorType(XmlAccessType.FIELD)
public class MedicalRecord extends MedicalDocument {
    private Patient patient;

    @XmlElementWrapper(name = "diagnoses")
    @XmlElement(name = "disease")
    private List<Disease> diseases;

    private Disease disease;

    private Staff createdBy;

    public MedicalRecord() {
    }

    public MedicalRecord(Long id, String documentType, Timestamp creationDate, Patient patient,Disease disease, List<Disease> diseases, Staff createdBy) {
        super(id, documentType, creationDate);
        this.patient = patient;
        this.disease = disease;
        this.diseases = diseases;
        this.createdBy = createdBy;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Disease> getDiseases() {
        return diseases;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }

    public Staff getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Staff createdBy) {
        this.createdBy = createdBy;
    }
}
