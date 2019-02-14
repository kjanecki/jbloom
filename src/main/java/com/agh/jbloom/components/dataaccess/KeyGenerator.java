package com.agh.jbloom.components.dataaccess;

import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class KeyGenerator {
    private ConnectionPool connectionPool;

    public KeyGenerator(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        }


    public IdentityField getKey(BaseInheritanceMapper bim) throws SQLException {
        var tableAccess=bim.getRelatedTableAccess();

        Connection connection=connectionPool.acquireConnection();
        StringBuilder quarry=new StringBuilder();

        quarry.append("Select max(").append(tableAccess.getPrimaryKey().getColumnScheme().getName()).append(") ");
        quarry.append("from ").append(tableAccess.getTableScheme().getName()).append(";");
        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery(quarry.toString());
        connectionPool.releaseConnection(connection);
        int id=0;
        if(resultSet.next()){
            id=resultSet.getInt(1)+1;
        }

        return new IdentityField(bim.getSubject(),id);
    }

}


