package org.solvd.service.interfaces;

import org.solvd.model.Patient;

public interface IPatientService {
    void registerNewPatient(Patient patient);
    Patient findPatientById(Long id);
}
