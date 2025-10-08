package com.solvd.hospital.dao;

import com.solvd.hospital.util.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDao {

    protected final ConnectionPool cp;

    protected BaseDao() throws SQLException {
        this.cp = ConnectionPool.getInstance();
    }

    protected Connection getConnection() {
        return cp.getConnection();
    }

    protected void releaseConnection(Connection connection) {
        if (connection != null) {
            cp.releaseConnection(connection);
        }
    }
}
