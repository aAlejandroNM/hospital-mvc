package org.solvd.service.impl;

import org.solvd.dao.impl.mysql.MedicalRecordDAO;
import org.solvd.dao.interfaces.IMedicalRecordDAO;
import org.solvd.model.MedicalRecord;
import org.solvd.service.interfaces.IDiagnosisService;

import java.sql.SQLException;

public class DiagnosisServiceImpl implements IDiagnosisService {
    private final IMedicalRecordDAO medicalRecordDAO;

    public DiagnosisServiceImpl() throws SQLException {
        this.medicalRecordDAO = new MedicalRecordDAO();
    }

    @Override
    public void recordDiagnosis(MedicalRecord medicalRecord) {
        medicalRecordDAO.create(medicalRecord);
    }
}
