package org.solvd.dao.impl.mysql;

import org.solvd.dao.BaseDao;
import org.solvd.dao.interfaces.ITreatmentDAO;
import org.solvd.model.Disease;
import org.solvd.model.Patient;
import org.solvd.model.Treatment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreatmentDAO extends BaseDao implements ITreatmentDAO {

    public TreatmentDAO() throws SQLException {
        super();
    }

    @Override
    public void create(Treatment treatment) {
        Connection conn = getConnection();
        try {
            String sql = "INSERT INTO treatment (patient_id, disease_id, name) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, treatment.getPatient().getId());
            ps.setLong(2, treatment.getDisease().getId());
            ps.setString(3, treatment.getName());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                treatment.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
    }

    @Override
    public Treatment getById(Long id) {
        Connection conn = getConnection();
        Treatment treatment = null;
        try {
            String sql = "SELECT t.id, t.name, t.created_at, " +
                    "p.id as patient_id, p.name as patient_name, p.age as patient_age, p.created_at as patient_created_at, pa.insurance_number, " +
                    "d.id as disease_id, d.name as disease_name, d.description as disease_description " +
                    "FROM treatment t " +
                    "JOIN person p ON t.patient_id = p.id " +
                    "JOIN patient pa ON t.patient_id = pa.id " +
                    "JOIN disease d ON t.disease_id = d.id " +
                    "WHERE t.id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Patient patient = new Patient(rs.getLong("patient_id"), rs.getString("patient_name"), rs.getInt("patient_age"), rs.getTimestamp("patient_created_at"), rs.getString("insurance_number"));
                Disease disease = new Disease(rs.getLong("disease_id"), rs.getString("disease_name"), rs.getString("disease_description"));
                treatment = new Treatment(rs.getLong("id"), patient, disease, rs.getString("name"), rs.getTimestamp("created_at"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
        return treatment;
    }

    @Override
    public List<Treatment> getAll() {
        Connection conn = getConnection();
        List<Treatment> treatments = new ArrayList<>();
        try {
            String sql = "SELECT t.id, t.name, t.created_at, " +
                    "p.id as patient_id, p.name as patient_name, p.age as patient_age, p.created_at as patient_created_at, pa.insurance_number, " +
                    "d.id as disease_id, d.name as disease_name, d.description as disease_description " +
                    "FROM treatment t " +
                    "JOIN person p ON t.patient_id = p.id " +
                    "JOIN patient pa ON t.patient_id = pa.id " +
                    "JOIN disease d ON t.disease_id = d.id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Patient patient = new Patient(rs.getLong("patient_id"), rs.getString("patient_name"), rs.getInt("patient_age"), rs.getTimestamp("patient_created_at"), rs.getString("insurance_number"));
                Disease disease = new Disease(rs.getLong("disease_id"), rs.getString("disease_name"), rs.getString("disease_description"));
                treatments.add(new Treatment(rs.getLong("id"), patient, disease, rs.getString("name"), rs.getTimestamp("created_at")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
        return treatments;
    }

    @Override
    public void update(Treatment treatment) {
        Connection conn = getConnection();
        try {
            String sql = "UPDATE treatment SET patient_id = ?, disease_id = ?, name = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, treatment.getPatient().getId());
            ps.setLong(2, treatment.getDisease().getId());
            ps.setString(3, treatment.getName());
            ps.setLong(4, treatment.getId());
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
            String sql = "DELETE FROM treatment WHERE id = ?";
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
