package com.solvd.hospital.dao.impl.mysql;

import com.solvd.hospital.dao.BaseDao;
import com.solvd.hospital.dao.interfaces.IMedicalRecordDAO;
import com.solvd.hospital.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecordDAO extends BaseDao implements IMedicalRecordDAO {

    public MedicalRecordDAO() throws SQLException {
        super();
    }

    @Override
    public void create(MedicalRecord medicalRecord) {
        Connection conn = getConnection();
        try {
            String docSql = "INSERT INTO medical_document (document_type) VALUES (?)";
            PreparedStatement psDoc = conn.prepareStatement(docSql, Statement.RETURN_GENERATED_KEYS);
            psDoc.setString(1, medicalRecord.getDocumentType());
            psDoc.executeUpdate();
            ResultSet rs = psDoc.getGeneratedKeys();
            long docId = 0;
            if (rs.next()) {
                docId = rs.getLong(1);
                medicalRecord.setId(docId);
            }

            String recordSql = "INSERT INTO medical_record (id, patient_id, disease_id, created_by_staff_id) VALUES (?, ?, ?, ?)";
            PreparedStatement psRecord = conn.prepareStatement(recordSql);
            psRecord.setLong(1, docId);
            psRecord.setLong(2, medicalRecord.getPatient().getId());
            psRecord.setLong(3, medicalRecord.getDisease().getId());
            psRecord.setLong(4, medicalRecord.getCreatedBy().getId());
            psRecord.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
    }

    @Override
    public MedicalRecord getById(Long id) {
        Connection conn = getConnection();
        MedicalRecord medicalRecord = null;
        try {
            String sql = "SELECT md.id, md.document_type, md.creation_date, " +
                    "p.id as patient_id, p.name as patient_name, p.age as patient_age, p.created_at as patient_created_at, pa.insurance_number, " +
                    "d.id as disease_id, d.name as disease_name, d.description as disease_description, " +
                    "s_p.id as staff_id, s_p.name as staff_name, s_p.age as staff_age, s_p.created_at as staff_created_at, s.hired_at, " +
                    "ms.id as specialty_id, ms.name as specialty_name " +
                    "FROM medical_document md " +
                    "JOIN medical_record mr ON md.id = mr.id " +
                    "JOIN person p ON mr.patient_id = p.id " +
                    "JOIN patient pa ON mr.patient_id = pa.id " +
                    "JOIN disease d ON mr.disease_id = d.id " +
                    "JOIN staff s ON mr.created_by_staff_id = s.id " +
                    "JOIN person s_p ON s.id = s_p.id " +
                    "JOIN medical_specialty ms ON s.specialty_id = ms.id " +
                    "WHERE md.id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Patient patient = new Patient(rs.getLong("patient_id"), rs.getString("patient_name"), rs.getInt("patient_age"), rs.getTimestamp("patient_created_at"), rs.getString("insurance_number"));
                Disease disease = new Disease(rs.getLong("disease_id"), rs.getString("disease_name"), rs.getString("disease_description"));
                MedicalSpecialty specialty = new MedicalSpecialty(rs.getInt("specialty_id"), rs.getString("specialty_name"));
                Staff staff = new Staff(rs.getLong("staff_id"), rs.getString("staff_name"), rs.getInt("staff_age"), rs.getTimestamp("staff_created_at"), specialty, rs.getDate("hired_at"));
                medicalRecord = new MedicalRecord(rs.getLong("id"), rs.getString("document_type"), rs.getTimestamp("creation_date"), patient, disease, staff);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
        return medicalRecord;
    }

    @Override
    public List<MedicalRecord> getAll() {
        Connection conn = getConnection();
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        try {
            String sql = "SELECT md.id, md.document_type, md.creation_date, " +
                    "p.id as patient_id, p.name as patient_name, p.age as patient_age, p.created_at as patient_created_at, pa.insurance_number, " +
                    "d.id as disease_id, d.name as disease_name, d.description as disease_description, " +
                    "s_p.id as staff_id, s_p.name as staff_name, s_p.age as staff_age, s_p.created_at as staff_created_at, s.hired_at, " +
                    "ms.id as specialty_id, ms.name as specialty_name " +
                    "FROM medical_document md " +
                    "JOIN medical_record mr ON md.id = mr.id " +
                    "JOIN person p ON mr.patient_id = p.id " +
                    "JOIN patient pa ON mr.patient_id = pa.id " +
                    "JOIN disease d ON mr.disease_id = d.id " +
                    "JOIN staff s ON mr.created_by_staff_id = s.id " +
                    "JOIN person s_p ON s.id = s_p.id " +
                    "JOIN medical_specialty ms ON s.specialty_id = ms.id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Patient patient = new Patient(rs.getLong("patient_id"), rs.getString("patient_name"), rs.getInt("patient_age"), rs.getTimestamp("patient_created_at"), rs.getString("insurance_number"));
                Disease disease = new Disease(rs.getLong("disease_id"), rs.getString("disease_name"), rs.getString("disease_description"));
                MedicalSpecialty specialty = new MedicalSpecialty(rs.getInt("specialty_id"), rs.getString("specialty_name"));
                Staff staff = new Staff(rs.getLong("staff_id"), rs.getString("staff_name"), rs.getInt("staff_age"), rs.getTimestamp("staff_created_at"), specialty, rs.getDate("hired_at"));
                medicalRecords.add(new MedicalRecord(rs.getLong("id"), rs.getString("document_type"), rs.getTimestamp("creation_date"), patient, disease, staff));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
        return medicalRecords;
    }

    @Override
    public void update(MedicalRecord medicalRecord) {
        Connection conn = getConnection();
        try {
            String docSql = "UPDATE medical_document SET document_type = ? WHERE id = ?";
            PreparedStatement psDoc = conn.prepareStatement(docSql);
            psDoc.setString(1, medicalRecord.getDocumentType());
            psDoc.setLong(2, medicalRecord.getId());
            psDoc.executeUpdate();

            String recordSql = "UPDATE medical_record SET patient_id = ?, disease_id = ?, created_by_staff_id = ? WHERE id = ?";
            PreparedStatement psRecord = conn.prepareStatement(recordSql);
            psRecord.setLong(1, medicalRecord.getPatient().getId());
            psRecord.setLong(2, medicalRecord.getDisease().getId());
            psRecord.setLong(3, medicalRecord.getCreatedBy().getId());
            psRecord.setLong(4, medicalRecord.getId());
            psRecord.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
    }

    @Override
    public void delete(Long id) {
        Connection conn = getConnection();
        try {
            String sql = "DELETE FROM medical_document WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
    }
}
