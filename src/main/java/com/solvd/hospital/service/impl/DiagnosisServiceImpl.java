package com.solvd.hospital.service.impl;

import com.solvd.hospital.dao.impl.mysql.MedicalRecordDAO;
import com.solvd.hospital.dao.interfaces.IMedicalRecordDAO;
import com.solvd.hospital.model.MedicalRecord;
import com.solvd.hospital.service.interfaces.IDiagnosisService;

import java.sql.SQLException;

public class DiagnosisServiceImpl implements IDiagnosisService {
    private final IMedicalRecordDAO medicalRecordDAO;

    public DiagnosisServiceImpl() throws SQLException {
        this.medicalRecordDAO = new MedicalRecordDAO();
    }

    public DiagnosisServiceImpl(IMedicalRecordDAO medicalRecordDAO) {
        this.medicalRecordDAO = medicalRecordDAO;
    }

    @Override
    public void recordDiagnosis(MedicalRecord medicalRecord) {
        medicalRecordDAO.create(medicalRecord);
    }
}
