package com.solvd.hospital.dao.impl.mysql;

import com.solvd.hospital.dao.BaseDao;
import com.solvd.hospital.dao.interfaces.ISymptomDAO;
import com.solvd.hospital.model.Symptom;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SymptomDAO extends BaseDao implements ISymptomDAO {

    public SymptomDAO() throws SQLException {
        super();
    }

    @Override
    public void create(Symptom symptom) {
        Connection conn = getConnection();
        try {
            String sql = "INSERT INTO symptom (name, description, severity) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, symptom.getName());
            ps.setString(2, symptom.getDescription());
            ps.setString(3, symptom.getSeverity());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                symptom.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
    }

    @Override
    public Symptom getById(Long id) {
        Connection conn = getConnection();
        Symptom symptom = null;
        try {
            String sql = "SELECT * FROM symptom WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                symptom = new Symptom(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("severity")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
        return symptom;
    }

    @Override
    public List<Symptom> getAll() {
        Connection conn = getConnection();
        List<Symptom> symptoms = new ArrayList<>();
        try {
            String sql = "SELECT * FROM symptom";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                symptoms.add(new Symptom(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("severity")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
        return symptoms;
    }

    @Override
    public void update(Symptom symptom) {
        Connection conn = getConnection();
        try {
            String sql = "UPDATE symptom SET name = ?, description = ?, severity = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, symptom.getName());
            ps.setString(2, symptom.getDescription());
            ps.setString(3, symptom.getSeverity());
            ps.setLong(4, symptom.getId());
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
            String sql = "DELETE FROM symptom WHERE id = ?";
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
