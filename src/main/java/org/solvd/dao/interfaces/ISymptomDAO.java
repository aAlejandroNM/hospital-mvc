package org.solvd.dao.interfaces;

import org.solvd.model.Symptom;

import java.util.List;

public interface ISymptomDAO {
    void create(Symptom symptom);
    Symptom getById(Long id);
    List<Symptom> getAll();
    void update(Symptom symptom);
    void delete(Long id);
}
