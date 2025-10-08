package com.solvd.hospital.dao.interfaces;

import com.solvd.hospital.model.Patient;

import java.util.List;

public interface IPatientDAO {
    void create(Patient patient);
    Patient getById(Long id);
    List<Patient> getAll();
    void update(Patient patient);
    void delete(Long id);
}
