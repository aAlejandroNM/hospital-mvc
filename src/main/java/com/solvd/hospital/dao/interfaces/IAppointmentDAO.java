package com.solvd.hospital.dao.interfaces;

import com.solvd.hospital.model.Appointment;

import java.util.List;

public interface IAppointmentDAO {
    void create(Appointment appointment);
    Appointment getById(Long id);
    List<Appointment> getAll();
    void update(Appointment appointment);
    void delete(Long id);
}
