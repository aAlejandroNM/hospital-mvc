package com.solvd.hospital.dao.interfaces;

import com.solvd.hospital.model.MedicalRecord;

import java.util.List;

public interface IMedicalRecordDAO {
    void create(MedicalRecord medicalRecord);
    MedicalRecord getById(Long id);
    List<MedicalRecord> getAll();
    void update(MedicalRecord medicalRecord);
    void delete(Long id);
}
