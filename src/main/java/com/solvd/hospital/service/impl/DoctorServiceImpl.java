package com.solvd.hospital.service.impl;

import com.solvd.hospital.dao.impl.mysql.DoctorDAO;
import com.solvd.hospital.dao.interfaces.IDoctorDAO;
import com.solvd.hospital.model.Doctor;
import com.solvd.hospital.service.interfaces.IDoctorService;

import java.sql.SQLException;
import java.util.List;

public class DoctorServiceImpl implements IDoctorService {
    private final IDoctorDAO doctorDAO;

    public DoctorServiceImpl() throws SQLException {
        this.doctorDAO = new DoctorDAO();
    }

    @Override
    public List<Doctor> getAvailableDoctors() {
        return doctorDAO.getAll();
    }
}
