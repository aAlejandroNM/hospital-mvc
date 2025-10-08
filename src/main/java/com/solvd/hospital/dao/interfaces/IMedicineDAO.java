package com.solvd.hospital.dao.interfaces;

import com.solvd.hospital.model.Medicine;

import java.util.List;

public interface IMedicineDAO {
    void create(Medicine medicine);
    Medicine getById(Long id);
    List<Medicine> getAll();
    void update(Medicine medicine);
    void delete(Long id);
}
