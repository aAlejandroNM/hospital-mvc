package com.solvd.hospital.strategy;

import com.solvd.hospital.model.Doctor;

import java.util.List;

public class FirstAvailableStrategy implements DoctorSelectionStrategy {
    @Override
    public Doctor select(List<Doctor> availableDoctors) {
        if (availableDoctors == null || availableDoctors.isEmpty()) {
            return null;
        }
        return availableDoctors.getFirst();
    }
}


