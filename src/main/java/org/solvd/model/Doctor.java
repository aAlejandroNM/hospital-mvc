package org.solvd.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Doctor extends Staff {
    private String licenseCode;

    public Doctor() {
    }

    public Doctor(Long id, String name, int age, Timestamp createdAt, MedicalSpecialty specialty, Date hiredAt, String licenseCode) {
        super(id, name, age, createdAt, specialty, hiredAt);
        this.licenseCode = licenseCode;
    }

    public String getLicenseCode() {
        return licenseCode;
    }

    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode;
    }
}
