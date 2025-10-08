package com.solvd.hospital.dao.impl.mysql;

import com.solvd.hospital.dao.BaseDao;
import com.solvd.hospital.dao.interfaces.IMedicineDAO;
import com.solvd.hospital.model.Medicine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineDAO extends BaseDao implements IMedicineDAO {

    public MedicineDAO() throws SQLException {
        super();
    }

    @Override
    public void create(Medicine medicine) {
        Connection conn = getConnection();
        try {
            String sql = "INSERT INTO medicine (name, quantity) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, medicine.getName());
            ps.setInt(2, medicine.getQuantity());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                medicine.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
    }

    @Override
    public Medicine getById(Long id) {
        Connection conn = getConnection();
        Medicine medicine = null;
        try {
            String sql = "SELECT * FROM medicine WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                medicine = new Medicine(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getTimestamp("updated_at")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
        return medicine;
    }

    @Override
    public List<Medicine> getAll() {
        Connection conn = getConnection();
        List<Medicine> medicines = new ArrayList<>();
        try {
            String sql = "SELECT * FROM medicine";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                medicines.add(new Medicine(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getTimestamp("updated_at")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(conn);
        }
        return medicines;
    }

    @Override
    public void update(Medicine medicine) {
        Connection conn = getConnection();
        try {
            String sql = "UPDATE medicine SET name = ?, quantity = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, medicine.getName());
            ps.setInt(2, medicine.getQuantity());
            ps.setLong(3, medicine.getId());
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
            String sql = "DELETE FROM medicine WHERE id = ?";
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
