package com.solvd.hospital.test.dao;

import com.solvd.hospital.dao.interfaces.IAppointmentDAO;
import com.solvd.hospital.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAppointmentDAO implements IAppointmentDAO {
    
    public final List<Appointment> appointments = new ArrayList<>();

    @Override
    public void create(Appointment appointment) {
        if (appointment.getId() == null) {
            appointment.setId((long) (appointments.size() + 1));
        }
        appointments.add(appointment);
    }

    @Override
    public Appointment getById(Long id) {
        return appointments.stream()
                .filter(a -> id.equals(a.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Appointment> getAll() {
        return new ArrayList<>(appointments);
    }

    @Override
    public void update(Appointment appointment) {
    }

    @Override
    public void delete(Long id) {
        appointments.removeIf(a -> id.equals(a.getId()));
    }

    public void clear() {
        appointments.clear();
    }
}

