package org.solvd.service.impl;

import org.solvd.dao.impl.mysql.AppointmentDAO;
import org.solvd.dao.interfaces.IAppointmentDAO;
import org.solvd.model.Appointment;
import org.solvd.service.interfaces.IAppointmentService;

import java.sql.SQLException;

public class AppointmentServiceImpl implements IAppointmentService {
    private final IAppointmentDAO appointmentDAO;

    public AppointmentServiceImpl() throws SQLException {
        this.appointmentDAO = new AppointmentDAO();
    }

    @Override
    public void scheduleAppointment(Appointment appointment) {
        appointmentDAO.create(appointment);
    }

    @Override
    public Appointment getAppointmentDetails(Long id) {
        return appointmentDAO.getById(id);
    }
}
