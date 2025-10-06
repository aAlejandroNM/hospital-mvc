package org.solvd.dao.impl.mysql;

import org.solvd.dao.BaseDao;
import org.solvd.dao.interfaces.IAppointmentDAO;
import org.solvd.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO extends BaseDao implements IAppointmentDAO {

    public AppointmentDAO() throws SQLException {
        super();
    }

    @Override
    public void create(Appointment appointment) {
        Connection conn = getConnection();
        try {
            String sql = "INSERT INTO appointment (patient_id, doctor_id, date, status_id) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, appointment.getPatient().getId());
            ps.setLong(2, appointment.getDoctor().getId());
            ps.setTimestamp(3, appointment.getDate());
            ps.setInt(4, appointment.getStatus().getId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                appointment.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
    }

    @Override
    public Appointment getById(Long id) {
        Connection conn = getConnection();
        Appointment appointment = null;
        try {
            String sql = "SELECT a.id, a.date, a.created_at, " +
                    "p.id as patient_id, p.name as patient_name, p.age as patient_age, p.created_at as patient_created_at, pa.insurance_number, " +
                    "d_p.id as doctor_id, d_p.name as doctor_name, d_p.age as doctor_age, d_p.created_at as doctor_created_at, s.hired_at, d.license_code, " +
                    "ms.id as specialty_id, ms.name as specialty_name, " +
                    "aps.id as status_id, aps.code as status_code " +
                    "FROM appointment a " +
                    "JOIN person p ON a.patient_id = p.id " +
                    "JOIN patient pa ON a.patient_id = pa.id " +
                    "JOIN doctor d ON a.doctor_id = d.staff_id " +
                    "JOIN staff s ON d.staff_id = s.id " +
                    "JOIN person d_p ON s.id = d_p.id " +
                    "JOIN medical_specialty ms ON s.specialty_id = ms.id " +
                    "JOIN appointment_status aps ON a.status_id = aps.id " +
                    "WHERE a.id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Patient patient = new Patient(rs.getLong("patient_id"), rs.getString("patient_name"), rs.getInt("patient_age"), rs.getTimestamp("patient_created_at"), rs.getString("insurance_number"));
                MedicalSpecialty specialty = new MedicalSpecialty(rs.getInt("specialty_id"), rs.getString("specialty_name"));
                Doctor doctor = new Doctor(rs.getLong("doctor_id"), rs.getString("doctor_name"), rs.getInt("doctor_age"), rs.getTimestamp("doctor_created_at"), specialty, rs.getDate("hired_at"), rs.getString("license_code"));
                AppointmentStatus status = new AppointmentStatus(rs.getInt("status_id"), rs.getString("status_code"));
                appointment = new Appointment(rs.getLong("id"), patient, doctor, rs.getTimestamp("date"), status, rs.getTimestamp("created_at"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
        return appointment;
    }

    @Override
    public List<Appointment> getAll() {
        Connection conn = getConnection();
        List<Appointment> appointments = new ArrayList<>();
        try {
            String sql = "SELECT a.id, a.date, a.created_at, " +
                    "p.id as patient_id, p.name as patient_name, p.age as patient_age, p.created_at as patient_created_at, pa.insurance_number, " +
                    "d_p.id as doctor_id, d_p.name as doctor_name, d_p.age as doctor_age, d_p.created_at as doctor_created_at, s.hired_at, d.license_code, " +
                    "ms.id as specialty_id, ms.name as specialty_name, " +
                    "aps.id as status_id, aps.code as status_code " +
                    "FROM appointment a " +
                    "JOIN person p ON a.patient_id = p.id " +
                    "JOIN patient pa ON a.patient_id = pa.id " +
                    "JOIN doctor d ON a.doctor_id = d.staff_id " +
                    "JOIN staff s ON d.staff_id = s.id " +
                    "JOIN person d_p ON s.id = d_p.id " +
                    "JOIN medical_specialty ms ON s.specialty_id = ms.id " +
                    "JOIN appointment_status aps ON a.status_id = aps.id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Patient patient = new Patient(rs.getLong("patient_id"), rs.getString("patient_name"), rs.getInt("patient_age"), rs.getTimestamp("patient_created_at"), rs.getString("insurance_number"));
                MedicalSpecialty specialty = new MedicalSpecialty(rs.getInt("specialty_id"), rs.getString("specialty_name"));
                Doctor doctor = new Doctor(rs.getLong("doctor_id"), rs.getString("doctor_name"), rs.getInt("doctor_age"), rs.getTimestamp("doctor_created_at"), specialty, rs.getDate("hired_at"), rs.getString("license_code"));
                AppointmentStatus status = new AppointmentStatus(rs.getInt("status_id"), rs.getString("status_code"));
                appointments.add(new Appointment(rs.getLong("id"), patient, doctor, rs.getTimestamp("date"), status, rs.getTimestamp("created_at")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
        return appointments;
    }

    @Override
    public void update(Appointment appointment) {
        Connection conn = getConnection();
        try {
            String sql = "UPDATE appointment SET patient_id = ?, doctor_id = ?, date = ?, status_id = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, appointment.getPatient().getId());
            ps.setLong(2, appointment.getDoctor().getId());
            ps.setTimestamp(3, appointment.getDate());
            ps.setInt(4, appointment.getStatus().getId());
            ps.setLong(5, appointment.getId());
            ps.executeUpdate();
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
            String sql = "DELETE FROM appointment WHERE id = ?";
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
