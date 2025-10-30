package com.solvd.hospital.strategy;

import com.solvd.hospital.model.Doctor;

import java.util.List;

public interface DoctorSelectionStrategy {
    Doctor select(List<Doctor> availableDoctors);
}


