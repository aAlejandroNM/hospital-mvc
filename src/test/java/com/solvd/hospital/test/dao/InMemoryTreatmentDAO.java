package com.solvd.hospital.test.dao;

import com.solvd.hospital.dao.interfaces.ITreatmentDAO;
import com.solvd.hospital.model.Treatment;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTreatmentDAO implements ITreatmentDAO {
    
    public final List<Treatment> treatments = new ArrayList<>();

    @Override
    public void create(Treatment treatment) {
        if (treatment.getId() == null) {
            treatment.setId((long) (treatments.size() + 1));
        }
        treatments.add(treatment);
    }

    @Override
    public Treatment getById(Long id) {
        return treatments.stream()
                .filter(t -> id.equals(t.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Treatment> getAll() {
        return new ArrayList<>(treatments);
    }

    @Override
    public void update(Treatment treatment) {
    }

    @Override
    public void delete(Long id) {
        treatments.removeIf(t -> id.equals(t.getId()));
    }

    public void clear() {
        treatments.clear();
    }
}

