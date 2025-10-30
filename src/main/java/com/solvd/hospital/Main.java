package com.solvd.hospital;

import com.solvd.hospital.model.*;
import com.solvd.hospital.service.ServiceFactory;
import com.solvd.hospital.service.interfaces.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        IPatientService patientService = ServiceFactory.createPatientService();
        IDoctorService doctorService = ServiceFactory.createDoctorService();
        IAppointmentService appointmentService = ServiceFactory.createAppointmentService();
        IDiagnosisService diagnosisService = ServiceFactory.createDiagnosisService();
        ITreatmentService treatmentService = ServiceFactory.createTreatmentService();
        IParserService saxParserService = ServiceFactory.createParserService();
        IXMLService xmlService = ServiceFactory.createXmlService();
        IJsonService jsonService = ServiceFactory.createJsonService();

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
            MedicalRecord medicalRecord = new MedicalRecord(null, "DIAGNOSIS", null, newPatient, diagnosedDisease,List.of(diagnosedDisease), assignedDoctor);
            diagnosisService.recordDiagnosis(medicalRecord);
            System.out.println("Diagnosis recorded. Medical Record ID: " + medicalRecord.getId());

            // Prescribe a treatment
            System.out.println("\n--- 5. Prescribing a treatment ---");
            Treatment newTreatment = new Treatment(null, newPatient, diagnosedDisease, "Prescription of beta-blockers", null);
            treatmentService.prescribeTreatment(newTreatment);
            System.out.println("Treatment prescribed with ID: " + newTreatment.getId());
        }

        System.out.println("\n--- Loading Medical Record using JAXB Parser ---");

        String medicalRecordXmlPath = "src/main/resources/xml/medical-record.xml";
        String medicalRecordXsdPath = "src/main/resources/xml/medical-record.xsd";
        MedicalRecord medicalRecordFromXml = xmlService.parseMedicalRecord(medicalRecordXmlPath, medicalRecordXsdPath);

        if (medicalRecordFromXml != null) {
            System.out.println("\nMedical Record loaded from XML:");
            medicalRecordFromXml.getDiseases()
                    .forEach(d -> System.out.println(" - " + d.getName() + ": " + d.getDescription()));
        } else {
            System.out.println("\nCould not parse Medical Record from XML.");
        }

        System.out.println("\n--- Loading catalogs using SAX Parser ---");
        //Parse symptoms
        String symptomsPath = "src/main/resources/xml/symptoms.xml";
        List<Symptom> symptomsFromXml = saxParserService.parseSymptoms(symptomsPath);
        System.out.println("\nSymptoms loaded from XML:");
        symptomsFromXml
                .forEach(s -> System.out.println(" - " + s.getName() + " (" + s.getSeverity() + "): " + s.getDescription()));

        System.out.println("\n--- Handling Medical Documents from JSON ---");
        String jsonFilePath = "src/main/resources/json/medical-document.json";

        System.out.println("\n--- Reading medical documents from JSON ---");
        List<MedicalDocument> medicalDocuments = jsonService.readMedicalDocuments(jsonFilePath);
        if (medicalDocuments.isEmpty()) {
            System.out.println("No medical documents found or error reading file.");
        } else {
            System.out.println("Medical documents loaded from JSON:");
            medicalDocuments.forEach(doc ->
                    System.out.println(" - ID: " + doc.getId() +
                            ", Type: " + doc.getDocumentType() +
                            ", Date: " + doc.getCreationDate()));
        }

        System.out.println("\n--- Adding a new medical document and writing to JSON ---");
        List<MedicalDocument> modifiableMedicalDocuments = new ArrayList<>(medicalDocuments);
        MedicalDocument newMedicalDocument = new MedicalDocument(4L, "CT Scan", Timestamp.from(Instant.now()));
        modifiableMedicalDocuments.add(newMedicalDocument);

        jsonService.writeMedicalDocuments(jsonFilePath, modifiableMedicalDocuments);
        System.out.println("New medical document added and list updated in JSON file.");

        System.out.println("\nHospital flow simulation finished.");
    }
}
