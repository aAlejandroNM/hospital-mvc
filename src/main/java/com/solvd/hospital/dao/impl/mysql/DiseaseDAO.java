package com.solvd.hospital.dao.impl.mysql;

import com.solvd.hospital.dao.BaseDao;
import com.solvd.hospital.dao.interfaces.IDiseaseDAO;
import com.solvd.hospital.model.Disease;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiseaseDAO extends BaseDao implements IDiseaseDAO {

    public DiseaseDAO() throws SQLException {
        super();
    }

    @Override
    public void create(Disease disease) {
        Connection conn = getConnection();
        try {
            String sql = "INSERT INTO disease (name, description) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, disease.getName());
            ps.setString(2, disease.getDescription());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                disease.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
    }

    @Override
    public Disease getById(Long id) {
        Connection conn = getConnection();
        Disease disease = null;
        try {
            String sql = "SELECT * FROM disease WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                disease = new Disease(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
        return disease;
    }

    @Override
    public List<Disease> getAll() {
        Connection conn = getConnection();
        List<Disease> diseases = new ArrayList<>();
        try {
            String sql = "SELECT * FROM disease";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                diseases.add(new Disease(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
        return diseases;
    }

    @Override
    public void update(Disease disease) {
        Connection conn = getConnection();
        try {
            String sql = "UPDATE disease SET name = ?, description = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, disease.getName());
            ps.setString(2, disease.getDescription());
            ps.setLong(3, disease.getId());
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
            String sql = "DELETE FROM disease WHERE id = ?";
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
