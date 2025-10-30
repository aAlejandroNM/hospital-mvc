package com.solvd.hospital.service.impl;

import com.solvd.hospital.dao.impl.mysql.TreatmentDAO;
import com.solvd.hospital.dao.interfaces.ITreatmentDAO;
import com.solvd.hospital.model.Treatment;
import com.solvd.hospital.service.interfaces.ITreatmentService;

import java.sql.SQLException;

public class TreatmentServiceImpl implements ITreatmentService {
    private final ITreatmentDAO treatmentDAO;

    public TreatmentServiceImpl() throws SQLException {
        this.treatmentDAO = new TreatmentDAO();
    }

    public TreatmentServiceImpl(ITreatmentDAO treatmentDAO) {
        this.treatmentDAO = treatmentDAO;
    }

    @Override
    public void prescribeTreatment(Treatment treatment) {
        treatmentDAO.create(treatment);
    }
}
