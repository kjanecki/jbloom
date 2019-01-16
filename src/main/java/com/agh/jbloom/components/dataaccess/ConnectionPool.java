package com.agh.jbloom.components.dataaccess;

import java.sql.Connection;
import java.util.Observable;

public class ConnectionPool extends Observable {

    private DataBaseConnector dataBaseConnector;

    public ConnectionPool(DataBaseConnector dataBaseConnector) {
        this.dataBaseConnector = dataBaseConnector;
    }

    public Connection getConnection(){
        return null;
    }

    public Connection acquireConnection(){
        return null;
    }

    public void releaseConnection(Connection connection){

    }
}
