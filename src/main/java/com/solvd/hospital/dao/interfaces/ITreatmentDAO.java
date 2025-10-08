package com.solvd.hospital.dao.interfaces;

import com.solvd.hospital.model.Treatment;

import java.util.List;

public interface ITreatmentDAO {
    void create(Treatment treatment);
    Treatment getById(Long id);
    List<Treatment> getAll();
    void update(Treatment treatment);
    void delete(Long id);
}
