package org.solvd.service.interfaces;

import org.solvd.model.Doctor;

import java.util.List;

public interface IDoctorService {
    List<Doctor> getAvailableDoctors();
}
