package com.solvd.hospital.strategy;

import com.solvd.hospital.model.Doctor;

import java.util.List;

public class BySpecialtyStrategy implements DoctorSelectionStrategy {
    private final String specialtyName;

    public BySpecialtyStrategy(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    @Override
    public Doctor select(List<Doctor> availableDoctors) {
        if (availableDoctors == null || availableDoctors.isEmpty()) {
            return null;
        }
        for (Doctor doctor : availableDoctors) {
            if (doctor.getSpecialty() != null && specialtyName != null && specialtyName.equalsIgnoreCase(doctor.getSpecialty().getName())) {
                return doctor;
            }
        }
        return availableDoctors.getFirst();
    }
}


