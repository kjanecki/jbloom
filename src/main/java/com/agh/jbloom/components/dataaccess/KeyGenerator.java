package com.agh.jbloom.components.dataaccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class KeyGenerator {
    private ConnectionPool connectionPool;
    private String classesTable;
    private String keyColumnName;
    private String classCloumnName;
    public KeyGenerator(ConnectionPool connectionPool, String classesTableName, String keyColumnName,String classCloumnName) {
        this.connectionPool = connectionPool;
        this.classesTable=classesTableName;
        this.keyColumnName=keyColumnName;
        this.classCloumnName=classCloumnName;
    }
    private Long getIdFromDB(Class subject) throws SQLException, NoIdException {
        Connection connection=connectionPool.acquireConnection();
        String className=subject.getName();
        StringBuilder quarry = new StringBuilder();
        quarry.append("Select ");
        quarry.append(keyColumnName);
        quarry.append(" From ");
        quarry.append(classesTable);
        quarry.append(" Where ");
        quarry.append(classCloumnName);
        quarry.append("=");
        quarry.append(className);
        quarry.append(" ;");

        Statement statement=connection.createStatement();
        ResultSet existResult=statement.executeQuery(quarry.toString());
        connectionPool.releaseConnection(connection);
        if(!existResult.next()) throw new NoIdException();
        else return existResult.getLong(1);
    }


    private void insertKeyIntoDB(Class subject, Long key) throws SQLException {
        StringBuilder quarry = new StringBuilder();
        Connection connection=connectionPool.acquireConnection();

        quarry.append("Insert Into ").append(classesTable);
        quarry.append("(").append(classCloumnName).append(", ").append(keyColumnName);
        quarry.append(" Values( ").append(subject.getName()).append(", ").append(key).append(");");

        Statement statement=connection.createStatement();
        statement.executeQuery(quarry.toString());
        connectionPool.releaseConnection(connection);

    }

    public IdentityField getKey(Class subject) throws SQLException {


        try {
            Long id= getIdFromDB(subject);
            return new IdentityField(subject,id);

        } catch (NoIdException e) {
            Connection connection=connectionPool.acquireConnection();
            StringBuilder quarry=new StringBuilder();

            quarry.append("Select max(").append(keyColumnName).append(") ");
            quarry.append("from ").append(classesTable).append(";");
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(quarry.toString());
            connectionPool.releaseConnection(connection);
            Long id=0L;
            if(resultSet.next()){
                id=resultSet.getLong(1)+1;
            }
            insertKeyIntoDB(subject,id);
            return new IdentityField(subject,id);
        }

    }
    private class NoIdException extends Exception{ }

}
