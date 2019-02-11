package com.agh.jbloom.components.dataaccess;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Observable;

public class ConnectionPool extends Observable {

    private DataSource dataSource;

    public ConnectionPool(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection acquireConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void releaseConnection(Connection connection){

    }
}
