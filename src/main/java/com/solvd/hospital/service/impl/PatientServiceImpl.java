package com.solvd.hospital.service.impl;

import com.solvd.hospital.dao.impl.mysql.PatientDAO;
import com.solvd.hospital.dao.interfaces.IPatientDAO;
import com.solvd.hospital.model.Patient;
import com.solvd.hospital.service.interfaces.IPatientService;

import java.sql.SQLException;

public class PatientServiceImpl implements IPatientService {
    private final IPatientDAO patientDAO;

    public PatientServiceImpl() throws SQLException {
        this.patientDAO = new PatientDAO();
    }

    @Override
    public void registerNewPatient(Patient patient) {
        patientDAO.create(patient);
    }

    @Override
    public Patient findPatientById(Long id) {
        return patientDAO.getById(id);
    }
}
