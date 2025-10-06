package org.solvd.dao.interfaces;

import org.solvd.model.Doctor;

import java.util.List;

public interface IDoctorDAO {
    void create(Doctor doctor);
    Doctor getById(Long id);
    List<Doctor> getAll();
    void update(Doctor doctor);
    void delete(Long id);
}
