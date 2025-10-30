package com.solvd.hospital.service.impl;

import com.solvd.hospital.dao.interfaces.IDoctorDAO;
import com.solvd.hospital.model.Doctor;
import com.solvd.hospital.service.interfaces.IDoctorService;

import java.util.List;

public class DoctorServiceImpl implements IDoctorService {

    private final IDoctorDAO doctorDAO;

    public DoctorServiceImpl(IDoctorDAO doctorDAO) {
        this.doctorDAO = doctorDAO;
    }

    @Override
    public List<Doctor> getAvailableDoctors() {
        return doctorDAO.getAll();
    }
}
