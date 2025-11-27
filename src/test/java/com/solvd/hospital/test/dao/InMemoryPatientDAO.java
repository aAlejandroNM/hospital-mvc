package com.solvd.hospital.test.dao;

import com.solvd.hospital.dao.interfaces.IPatientDAO;
import com.solvd.hospital.model.Patient;

import java.util.ArrayList;
import java.util.List;


public class InMemoryPatientDAO implements IPatientDAO {

    private final List<Patient> patients = new ArrayList<>();
    private final List<Patient> createdPatients = new ArrayList<>();

    @Override
    public void create(Patient patient) {
        createdPatients.add(patient);
        if (patient.getId() == null) {
            patient.setId((long) (patients.size() + 1));
        }
        patients.add(patient);
    }

    @Override
    public Patient getById(Long id) {
        return patients.stream()
                .filter(p -> id.equals(p.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Patient> getAll() {
        return new ArrayList<>(patients);
    }

    @Override
    public void update(Patient patient) {
    }

    @Override
    public void delete(Long id) {
        patients.removeIf(p -> id.equals(p.getId()));
    }

    public List<Patient> getCreatedPatients() {
        return new ArrayList<>(createdPatients);
    }

    public List<Patient> getPatients() {
        return new ArrayList<>(patients);
    }

    public void clear() {
        patients.clear();
        createdPatients.clear();
    }
}

