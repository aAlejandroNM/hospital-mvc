package org.solvd.dao.interfaces;

import org.solvd.model.Disease;

import java.util.List;

public interface IDiseaseDAO {
    void create(Disease disease);
    Disease getById(Long id);
    List<Disease> getAll();
    void update(Disease disease);
    void delete(Long id);
}
