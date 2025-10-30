package com.solvd.hospital.service;

import com.solvd.hospital.dao.impl.mybatis.DoctorMyBatisImpl;
import com.solvd.hospital.dao.impl.mybatis.PatientMyBatisImpl;
import com.solvd.hospital.dao.impl.mysql.AppointmentDAO;
import com.solvd.hospital.dao.impl.mysql.MedicalRecordDAO;
import com.solvd.hospital.dao.impl.mysql.TreatmentDAO;
import com.solvd.hospital.dao.interfaces.IDoctorDAO;
import com.solvd.hospital.dao.interfaces.IPatientDAO;
import com.solvd.hospital.service.impl.*;
import com.solvd.hospital.service.interfaces.*;

public class ServiceFactory {

    public static IPatientService createPatientService() {
        IPatientDAO patientDAO = new PatientMyBatisImpl();
        return new PatientServiceImpl(patientDAO);
    }

    public static IDoctorService createDoctorService() {
        IDoctorDAO doctorDAO = new DoctorMyBatisImpl();
        return new DoctorServiceImpl(doctorDAO);
    }

    public static IAppointmentService createAppointmentService() {
        try {
            return new AppointmentServiceImpl(new AppointmentDAO());
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static IDiagnosisService createDiagnosisService() {
        try {
            return new DiagnosisServiceImpl(new MedicalRecordDAO());
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ITreatmentService createTreatmentService() {
        try {
            return new TreatmentServiceImpl(new TreatmentDAO());
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static IParserService createParserService() {
        return new SAXParserServiceImpl();
    }

    public static IXMLService createXmlService() {
        return new XMLServiceImpl();
    }

    public static IJsonService createJsonService() {
        return new JsonServiceImpl();
    }
}
