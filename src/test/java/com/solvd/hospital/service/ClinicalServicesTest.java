package com.solvd.hospital.service;

import com.solvd.hospital.model.*;
import com.solvd.hospital.service.impl.AppointmentServiceImpl;
import com.solvd.hospital.service.impl.DiagnosisServiceImpl;
import com.solvd.hospital.service.impl.DoctorServiceImpl;
import com.solvd.hospital.service.impl.TreatmentServiceImpl;
import com.solvd.hospital.test.dao.InMemoryAppointmentDAO;
import com.solvd.hospital.test.dao.InMemoryDoctorDAO;
import com.solvd.hospital.test.dao.InMemoryMedicalRecordDAO;
import com.solvd.hospital.test.dao.InMemoryTreatmentDAO;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

public class ClinicalServicesTest {

    private InMemoryDoctorDAO doctorDAO;
    private InMemoryAppointmentDAO appointmentDAO;
    private InMemoryMedicalRecordDAO medicalRecordDAO;
    private InMemoryTreatmentDAO treatmentDAO;

    private DoctorServiceImpl doctorService;
    private AppointmentServiceImpl appointmentService;
    private DiagnosisServiceImpl diagnosisService;
    private TreatmentServiceImpl treatmentService;

    @BeforeClass(alwaysRun = true)
    public void initServices() {
        doctorDAO = new InMemoryDoctorDAO();
        appointmentDAO = new InMemoryAppointmentDAO();
        medicalRecordDAO = new InMemoryMedicalRecordDAO();
        treatmentDAO = new InMemoryTreatmentDAO();

        doctorService = new DoctorServiceImpl(doctorDAO);
        appointmentService = new AppointmentServiceImpl(appointmentDAO);
        diagnosisService = new DiagnosisServiceImpl(medicalRecordDAO);
        treatmentService = new TreatmentServiceImpl(treatmentDAO);
    }

    @AfterClass
    public void clearResources() {
        if (doctorDAO != null) doctorDAO.clear();
        if (appointmentDAO != null) appointmentDAO.clear();
        if (medicalRecordDAO != null) medicalRecordDAO.clear();
        if (treatmentDAO != null) treatmentDAO.clear();
    }

    @Test(groups = {"service", "doctor"})
    public void getAvailableDoctorsShouldReturnDoctorsFromDao() {
        Doctor doctor = new Doctor(1L, "Dr. House", 20, Timestamp.from(Instant.now()), new MedicalSpecialty(1, "Diagnostics"), new Date(System.currentTimeMillis()), "ASDF");
        doctorDAO.doctors.add(doctor);

        List<Doctor> result = doctorService.getAvailableDoctors();

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertSame(result.get(0), doctor);
    }

    @Test(priority = 1, groups = {"service", "appointment"})
    public void scheduleAppointmentShouldDelegateToDao() {
        Patient patient = new Patient(1L, "Patient A", 40, null, "INS-1");
        Doctor doctor = new Doctor(2L, "Dr. Who", 20, Timestamp.from(Instant.now()), new MedicalSpecialty(2, "Time Travel"), new Date(System.currentTimeMillis()), "ASDF");
        AppointmentStatus status = new AppointmentStatus(1, "SCHEDULED");
        Appointment appointment = Appointment.builder()
                .id(null)
                .patient(patient)
                .doctor(doctor)
                .date(Timestamp.from(Instant.now()))
                .status(status)
                .createdAt(null)
                .build();

        appointmentService.scheduleAppointment(appointment);

        assertEquals(appointmentDAO.appointments.size(), 1);
        assertSame(appointment, appointmentDAO.appointments.get(0));
    }

    @Test(priority = 2, dependsOnMethods = "scheduleAppointmentShouldDelegateToDao", groups = {"service", "appointment"})
    public void getAppointmentDetailsShouldReturnFromDao() {
        Appointment stored = appointmentDAO.appointments.get(0);

        Appointment result = appointmentService.getAppointmentDetails(stored.getId());

        assertSame(result, stored);
    }

    @Test(groups = {"service", "diagnosis"})
    public void recordDiagnosisShouldCreateMedicalRecord() {
        Patient patient = new Patient(2L, "Patient B", 35, null, "INS-2");
        Disease disease = new Disease(1L, "Hypertension", "High blood pressure");
        Doctor doctor = new Doctor(3L, "Dr. Strange", 20, Timestamp.from(Instant.now()), new MedicalSpecialty(3, "Magic"), new Date(System.currentTimeMillis()), "ASDF");
        MedicalRecord record = new MedicalRecord(null, "DIAGNOSIS", null, patient, disease, List.of(disease), doctor);

        diagnosisService.recordDiagnosis(record);

        assertEquals(medicalRecordDAO.records.size(), 1);
        assertSame(medicalRecordDAO.records.get(0), record);
    }

    @Test(groups = {"service", "treatment"})
    public void prescribeTreatmentShouldDelegateToDao() {
        Patient patient = new Patient(3L, "Patient C", 50, null, "INS-3");
        Disease disease = new Disease(2L, "Diabetes", "High blood sugar");
        Treatment treatment = new Treatment(null, patient, disease, "Insulin regimen", null);

        treatmentService.prescribeTreatment(treatment);

        assertEquals(treatmentDAO.treatments.size(), 1);
        assertSame(treatmentDAO.treatments.get(0), treatment);
    }
}
