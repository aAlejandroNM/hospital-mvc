package com.solvd.hospital.service.impl;

import com.solvd.hospital.dao.impl.mysql.AppointmentDAO;
import com.solvd.hospital.dao.interfaces.IAppointmentDAO;
import com.solvd.hospital.model.Appointment;
import com.solvd.hospital.service.interfaces.IAppointmentService;

import java.sql.SQLException;

public class AppointmentServiceImpl implements IAppointmentService {
    private final IAppointmentDAO appointmentDAO;

    public AppointmentServiceImpl() throws SQLException {
        this.appointmentDAO = new AppointmentDAO();
    }

    public AppointmentServiceImpl(IAppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
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
