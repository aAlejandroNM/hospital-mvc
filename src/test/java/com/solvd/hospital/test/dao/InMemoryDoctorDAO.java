package com.solvd.hospital.test.dao;

import com.solvd.hospital.dao.interfaces.IDoctorDAO;
import com.solvd.hospital.model.Doctor;

import java.util.ArrayList;
import java.util.List;

public class InMemoryDoctorDAO implements IDoctorDAO {
    
    public final List<Doctor> doctors = new ArrayList<>();

    @Override
    public void create(Doctor doctor) {
        doctors.add(doctor);
    }

    @Override
    public Doctor getById(Long id) {
        return doctors.stream()
                .filter(d -> id.equals(d.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Doctor> getAll() {
        return new ArrayList<>(doctors);
    }

    @Override
    public void update(Doctor doctor) {
    }

    @Override
    public void delete(Long id) {
        doctors.removeIf(d -> id.equals(d.getId()));
    }

    public void clear() {
        doctors.clear();
    }
}

