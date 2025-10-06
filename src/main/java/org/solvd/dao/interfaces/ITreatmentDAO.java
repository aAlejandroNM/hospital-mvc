package org.solvd.dao.interfaces;

import org.solvd.model.Treatment;

import java.util.List;

public interface ITreatmentDAO {
    void create(Treatment treatment);
    Treatment getById(Long id);
    List<Treatment> getAll();
    void update(Treatment treatment);
    void delete(Long id);
}
