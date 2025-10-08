package com.solvd.hospital.dao.interfaces;

import com.solvd.hospital.model.Doctor;

import java.util.List;

public interface IDoctorDAO {
    void create(Doctor doctor);
    Doctor getById(Long id);
    List<Doctor> getAll();
    void update(Doctor doctor);
    void delete(Long id);
}
