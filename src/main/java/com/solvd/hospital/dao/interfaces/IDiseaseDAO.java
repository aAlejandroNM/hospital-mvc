package com.solvd.hospital.dao.interfaces;

import com.solvd.hospital.model.Disease;

import java.util.List;

public interface IDiseaseDAO {
    void create(Disease disease);
    Disease getById(Long id);
    List<Disease> getAll();
    void update(Disease disease);
    void delete(Long id);
}
