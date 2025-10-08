package com.solvd.hospital.service.interfaces;

import com.solvd.hospital.model.Doctor;

import java.util.List;

public interface IDoctorService {
    List<Doctor> getAvailableDoctors();
}
