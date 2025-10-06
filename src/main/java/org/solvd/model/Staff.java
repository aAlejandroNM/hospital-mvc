package org.solvd.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Staff extends Person {
    private MedicalSpecialty specialty;
    private Date hiredAt;

    public Staff() {
    }

    public Staff(Long id, String name, int age, Timestamp createdAt, MedicalSpecialty specialty, Date hiredAt) {
        super(id, name, age, createdAt);
        this.specialty = specialty;
        this.hiredAt = hiredAt;
    }

    public MedicalSpecialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(MedicalSpecialty specialty) {
        this.specialty = specialty;
    }

    public Date getHiredAt() {
        return hiredAt;
    }

    public void setHiredAt(Date hiredAt) {
        this.hiredAt = hiredAt;
    }
}
