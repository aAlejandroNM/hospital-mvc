package org.solvd.dao.interfaces;

import org.solvd.model.MedicalRecord;

import java.util.List;

public interface IMedicalRecordDAO {
    void create(MedicalRecord medicalRecord);
    MedicalRecord getById(Long id);
    List<MedicalRecord> getAll();
    void update(MedicalRecord medicalRecord);
    void delete(Long id);
}
