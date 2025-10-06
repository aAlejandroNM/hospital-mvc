package org.solvd.dao.impl.mysql;

import org.solvd.dao.BaseDao;
import org.solvd.dao.interfaces.IDoctorDAO;
import org.solvd.model.Doctor;
import org.solvd.model.MedicalSpecialty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO extends BaseDao implements IDoctorDAO {

    public DoctorDAO() throws SQLException {
        super();
    }

    @Override
    public void create(Doctor doctor) {
        Connection conn = getConnection();
        try {
            String personSql = "INSERT INTO person (name, age) VALUES (?, ?)";
            PreparedStatement psPerson = conn.prepareStatement(personSql, Statement.RETURN_GENERATED_KEYS);
            psPerson.setString(1, doctor.getName());
            psPerson.setInt(2, doctor.getAge());
            psPerson.executeUpdate();
            ResultSet rs = psPerson.getGeneratedKeys();
            long personId = 0;
            if (rs.next()) {
                personId = rs.getLong(1);
                doctor.setId(personId);
            }

            String staffSql = "INSERT INTO staff (id, specialty_id, hired_at) VALUES (?, ?, ?)";
            PreparedStatement psStaff = conn.prepareStatement(staffSql);
            psStaff.setLong(1, personId);
            psStaff.setInt(2, doctor.getSpecialty().getId());
            psStaff.setDate(3, doctor.getHiredAt());
            psStaff.executeUpdate();

            String doctorSql = "INSERT INTO doctor (staff_id, license_code) VALUES (?, ?)";
            PreparedStatement psDoctor = conn.prepareStatement(doctorSql);
            psDoctor.setLong(1, personId);
            psDoctor.setString(2, doctor.getLicenseCode());
            psDoctor.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
    }

    @Override
    public Doctor getById(Long id) {
        Connection conn = getConnection();
        Doctor doctor = null;
        try {
            String sql = "SELECT p.id, p.name, p.age, p.created_at, s.hired_at, d.license_code, ms.id as specialty_id, ms.name as specialty_name " +
                         "FROM person p " +
                         "JOIN staff s ON p.id = s.id " +
                         "JOIN doctor d ON s.id = d.staff_id " +
                         "JOIN medical_specialty ms ON s.specialty_id = ms.id " +
                         "WHERE p.id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                MedicalSpecialty specialty = new MedicalSpecialty(rs.getInt("specialty_id"), rs.getString("specialty_name"));
                doctor = new Doctor(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getTimestamp("created_at"),
                        specialty,
                        rs.getDate("hired_at"),
                        rs.getString("license_code")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
        return doctor;
    }

    @Override
    public List<Doctor> getAll() {
        Connection conn = getConnection();
        List<Doctor> doctors = new ArrayList<>();
        try {
            String sql = "SELECT p.id, p.name, p.age, p.created_at, s.hired_at, d.license_code, ms.id as specialty_id, ms.name as specialty_name " +
                         "FROM person p " +
                         "JOIN staff s ON p.id = s.id " +
                         "JOIN doctor d ON s.id = d.staff_id " +
                         "JOIN medical_specialty ms ON s.specialty_id = ms.id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MedicalSpecialty specialty = new MedicalSpecialty(rs.getInt("specialty_id"), rs.getString("specialty_name"));
                doctors.add(new Doctor(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getTimestamp("created_at"),
                        specialty,
                        rs.getDate("hired_at"),
                        rs.getString("license_code")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
        return doctors;
    }

    @Override
    public void update(Doctor doctor) {
        Connection conn = getConnection();
        try {
            String personSql = "UPDATE person SET name = ?, age = ? WHERE id = ?";
            PreparedStatement psPerson = conn.prepareStatement(personSql);
            psPerson.setString(1, doctor.getName());
            psPerson.setInt(2, doctor.getAge());
            psPerson.setLong(3, doctor.getId());
            psPerson.executeUpdate();

            String staffSql = "UPDATE staff SET specialty_id = ?, hired_at = ? WHERE id = ?";
            PreparedStatement psStaff = conn.prepareStatement(staffSql);
            psStaff.setInt(1, doctor.getSpecialty().getId());
            psStaff.setDate(2, doctor.getHiredAt());
            psStaff.setLong(3, doctor.getId());
            psStaff.executeUpdate();

            String doctorSql = "UPDATE doctor SET license_code = ? WHERE staff_id = ?";
            PreparedStatement psDoctor = conn.prepareStatement(doctorSql);
            psDoctor.setString(1, doctor.getLicenseCode());
            psDoctor.setLong(2, doctor.getId());
            psDoctor.executeUpdate();

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
