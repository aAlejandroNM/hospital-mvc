package com.solvd.hospital.test.dao;

import com.solvd.hospital.dao.interfaces.IMedicalRecordDAO;
import com.solvd.hospital.model.MedicalRecord;

import java.util.ArrayList;
import java.util.List;

public class InMemoryMedicalRecordDAO implements IMedicalRecordDAO {
    
    public final List<MedicalRecord> records = new ArrayList<>();

    @Override
    public void create(MedicalRecord medicalRecord) {
        if (medicalRecord.getId() == null) {
            medicalRecord.setId((long) (records.size() + 1));
        }
        records.add(medicalRecord);
    }

    @Override
    public MedicalRecord getById(Long id) {
        return records.stream()
                .filter(r -> id.equals(r.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<MedicalRecord> getAll() {
        return new ArrayList<>(records);
    }

    @Override
    public void update(MedicalRecord medicalRecord) {
    }

    @Override
    public void delete(Long id) {
        records.removeIf(r -> id.equals(r.getId()));
    }

    public void clear() {
        records.clear();
    }
}

