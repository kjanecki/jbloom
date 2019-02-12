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

    public IdentityField getNewKey(Class subject) throws SQLException {
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

        if(!existResult.next()){
            //This class isn't in table
            quarry.setLength(0);
            quarry.append("Select max(").append(keyColumnName).append(") ");
            quarry.append("from ").append(classesTable).append(";");
            statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(quarry.toString());
            Long id;
            if(!resultSet.next()){
                //There are no entries in table
                id=0L;
            }
            else {
                //Other way get max id+1
                id=resultSet.getLong(1)+1;
            }
            //Update table with new class
            quarry.setLength(0);
            quarry.append("Insert Into ").append(classesTable);
            quarry.append("(").append(classCloumnName).append(", ").append(keyColumnName);
            quarry.append(" Values( ").append(className).append(", ").append(id).append(");");
            statement=connection.createStatement();
            statement.executeQuery(quarry.toString());
            return new IdentityField(subject,id);


        }else {
            Long result_key=existResult.getLong(1);
            return new IdentityField(subject,result_key);

        }


    }





}
