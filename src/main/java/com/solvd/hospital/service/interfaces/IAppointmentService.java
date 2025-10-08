package com.solvd.hospital.service.interfaces;

import com.solvd.hospital.model.Appointment;

public interface IAppointmentService {
    void scheduleAppointment(Appointment appointment);
    Appointment getAppointmentDetails(Long id);
}
