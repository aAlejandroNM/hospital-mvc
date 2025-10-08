package com.solvd.hospital.dao.impl.mysql;

import com.solvd.hospital.dao.BaseDao;
import com.solvd.hospital.dao.interfaces.IPatientDAO;
import com.solvd.hospital.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO extends BaseDao implements IPatientDAO {

    public PatientDAO() throws SQLException {
        super();
    }

    @Override
    public void create(Patient patient) {
        Connection conn = getConnection();
        try {
            String personSql = "INSERT INTO person (name, age) VALUES (?, ?)";
            PreparedStatement psPerson = conn.prepareStatement(personSql, Statement.RETURN_GENERATED_KEYS);
            psPerson.setString(1, patient.getName());
            psPerson.setInt(2, patient.getAge());
            psPerson.executeUpdate();

            ResultSet rs = psPerson.getGeneratedKeys();
            long personId = 0;
            if (rs.next()) {
                personId = rs.getLong(1);
                patient.setId(personId);
            }

            String patientSql = "INSERT INTO patient (id, insurance_number) VALUES (?, ?)";
            PreparedStatement psPatient = conn.prepareStatement(patientSql);
            psPatient.setLong(1, personId);
            psPatient.setString(2, patient.getInsuranceNumber());
            psPatient.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
    }

    @Override
    public Patient getById(Long id) {
        Connection conn = getConnection();
        Patient patient = null;
        try {
            String sql = "SELECT p.id, p.name, p.age, p.created_at, pa.insurance_number FROM person p JOIN patient pa ON p.id = pa.id WHERE p.id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                patient = new Patient(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getTimestamp("created_at"),
                        rs.getString("insurance_number")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
        return patient;
    }

    @Override
    public List<Patient> getAll() {
        Connection conn = getConnection();
        List<Patient> patients = new ArrayList<>();
        try {
            String sql = "SELECT p.id, p.name, p.age, p.created_at, pa.insurance_number FROM person p JOIN patient pa ON p.id = pa.id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                patients.add(new Patient(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getTimestamp("created_at"),
                        rs.getString("insurance_number")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
        return patients;
    }

    @Override
    public void update(Patient patient) {
        Connection conn = getConnection();
        try {
            String personSql = "UPDATE person SET name = ?, age = ? WHERE id = ?";
            PreparedStatement psPerson = conn.prepareStatement(personSql);
            psPerson.setString(1, patient.getName());
            psPerson.setInt(2, patient.getAge());
            psPerson.setLong(3, patient.getId());
            psPerson.executeUpdate();

            String patientSql = "UPDATE patient SET insurance_number = ? WHERE id = ?";
            PreparedStatement psPatient = conn.prepareStatement(patientSql);
            psPatient.setString(1, patient.getInsuranceNumber());
            psPatient.setLong(2, patient.getId());
            psPatient.executeUpdate();

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
            String sql = "DELETE FROM person WHERE id = ?";
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
