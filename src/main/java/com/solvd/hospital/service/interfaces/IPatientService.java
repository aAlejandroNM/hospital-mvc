package com.solvd.hospital.service.interfaces;

import com.solvd.hospital.model.Patient;

public interface IPatientService {
    void registerNewPatient(Patient patient);
    Patient findPatientById(Long id);
}
