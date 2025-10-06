package org.solvd.service.impl;

import org.solvd.dao.impl.mysql.DoctorDAO;
import org.solvd.dao.interfaces.IDoctorDAO;
import org.solvd.model.Doctor;
import org.solvd.service.interfaces.IDoctorService;

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
