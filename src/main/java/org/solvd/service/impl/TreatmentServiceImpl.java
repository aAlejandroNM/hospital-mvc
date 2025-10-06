package org.solvd.service.impl;

import org.solvd.dao.impl.mysql.TreatmentDAO;
import org.solvd.dao.interfaces.ITreatmentDAO;
import org.solvd.model.Treatment;
import org.solvd.service.interfaces.ITreatmentService;

import java.sql.SQLException;

public class TreatmentServiceImpl implements ITreatmentService {
    private final ITreatmentDAO treatmentDAO;

    public TreatmentServiceImpl() throws SQLException {
        this.treatmentDAO = new TreatmentDAO();
    }

    @Override
    public void prescribeTreatment(Treatment treatment) {
        treatmentDAO.create(treatment);
    }
}
