package com.solvd.hospital.dao.interfaces;

import com.solvd.hospital.model.Symptom;

import java.util.List;

public interface ISymptomDAO {
    void create(Symptom symptom);
    Symptom getById(Long id);
    List<Symptom> getAll();
    void update(Symptom symptom);
    void delete(Long id);
}
