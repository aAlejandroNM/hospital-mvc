package com.solvd.hospital;

import com.solvd.hospital.model.*;
import com.solvd.hospital.service.impl.SAXParserServiceImpl;
import com.solvd.hospital.service.impl.*;
import com.solvd.hospital.service.interfaces.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        IPatientService patientService = new PatientServiceImpl();
        IDoctorService doctorService = new DoctorServiceImpl();
        IAppointmentService appointmentService = new AppointmentServiceImpl();
        IDiagnosisService diagnosisService = new DiagnosisServiceImpl();
        ITreatmentService treatmentService = new TreatmentServiceImpl();
        IParserService saxParserService = new SAXParserServiceImpl();

        //Register a new patient
        System.out.println("\n--- 1. Registering a new patient ");
        Patient newPatient = new Patient(null, "John Doe", 30, null, "INS-12345");
        patientService.registerNewPatient(newPatient);
        System.out.println("New patient registered with ID: " + newPatient.getId());

        //Find an available doctor
        System.out.println("\n--- 2. Finding an available doctor ---");
        List<Doctor> doctors = doctorService.getAvailableDoctors();
        if (doctors.isEmpty()) {
            System.out.println("No doctors available. Exiting.");
            System.out.println("No doctors found. Please populate the database.");
        } else {
            Doctor assignedDoctor = doctors.getFirst();
            System.out.println("Assigned doctor: " + assignedDoctor.getName() + " (" + assignedDoctor.getSpecialty().getName() + ")");

            //Schedule an appointment
            System.out.println("\n--- 3. Scheduling an appointment ---");
            AppointmentStatus status = new AppointmentStatus(1, "SCHEDULED");
            Appointment newAppointment = new Appointment(null, newPatient, assignedDoctor, Timestamp.from(Instant.now()), status, null);
            appointmentService.scheduleAppointment(newAppointment);
            System.out.println("New appointment scheduled with ID: " + newAppointment.getId());

            //Diagnose the patient
            System.out.println("\n 4. Diagnosing the patient ---");
            Disease diagnosedDisease = new Disease(1L, "Hypertension", "High blood pressure");
            MedicalRecord medicalRecord = new MedicalRecord(null, "DIAGNOSIS", null, newPatient, diagnosedDisease, assignedDoctor);
            diagnosisService.recordDiagnosis(medicalRecord);
            System.out.println("Diagnosis recorded. Medical Record ID: " + medicalRecord.getId());

            // Prescribe a treatment
            System.out.println("\n--- 5. Prescribing a treatment ---");
            Treatment newTreatment = new Treatment(null, newPatient, diagnosedDisease, "Prescription of beta-blockers", null);
            treatmentService.prescribeTreatment(newTreatment);
            System.out.println("Treatment prescribed with ID: " + newTreatment.getId());
        }

        System.out.println("\n--- Loading catalogs using SAX Parser ---");

        // Parse diseases
        String diseasesPath = "src/main/resources/xml/diseases.xml";
        List<Disease> diseasesFromXml = saxParserService.parseDiseases(diseasesPath);
        System.out.println("\nDiseases loaded from XML:");
        diseasesFromXml
                .forEach(d -> System.out.println(" - " + d.getName() + ": " + d.getDescription()));

        //Parse symptoms
        String symptomsPath = "src/main/resources/xml/symptoms.xml";
        List<Symptom> symptomsFromXml = saxParserService.parseSymptoms(symptomsPath);
        System.out.println("\nSymptoms loaded from XML:");
        symptomsFromXml
                .forEach(s -> System.out.println(" - " + s.getName() + " (" + s.getSeverity() + "): " + s.getDescription()));

        System.out.println("\nHospital flow simulation finished.");
    }
}
