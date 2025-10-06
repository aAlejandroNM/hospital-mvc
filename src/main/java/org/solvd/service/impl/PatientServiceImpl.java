package org.solvd.service.impl;

import org.solvd.dao.impl.mysql.PatientDAO;
import org.solvd.dao.interfaces.IPatientDAO;
import org.solvd.model.Patient;
import org.solvd.service.interfaces.IPatientService;

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
