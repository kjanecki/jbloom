package com.agh.jbloom.components.query;

import com.agh.jbloom.components.dataaccess.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Transaction{

    private List<String> queries;
    private ConnectionPool connectionPool;

    public Transaction(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        this.queries = new ArrayList<>();
    }

    public void addQuery(String query){
        queries.add(query);
    }

    public void commit() throws SQLException {
        Connection conn = connectionPool.acquireConnection();
        conn.setAutoCommit(false);
        for(var query : queries){
            conn.createStatement().executeUpdate(query);
        }
        conn.commit();
    }
}
