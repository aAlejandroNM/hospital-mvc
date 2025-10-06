package org.solvd.service.interfaces;

import org.solvd.model.Appointment;

public interface IAppointmentService {
    void scheduleAppointment(Appointment appointment);
    Appointment getAppointmentDetails(Long id);
}
