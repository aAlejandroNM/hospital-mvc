package com.solvd.hospital.model;

import java.sql.Timestamp;

public class MedicalDocument {
    private Long id;
    private String documentType;
    private Timestamp creationDate;

    public MedicalDocument() {
    }

    public MedicalDocument(Long id, String documentType, Timestamp creationDate) {
        this.id = id;
        this.documentType = documentType;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
