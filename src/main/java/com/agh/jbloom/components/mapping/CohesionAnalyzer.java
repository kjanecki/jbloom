package com.agh.jbloom.components.mapping;

import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.mapping.mappers.TableAccess;
import com.agh.jbloom.components.mapping.model.TableScheme;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CohesionAnalyzer {

    private ConnectionPool connectionPool;

    public CohesionAnalyzer(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public boolean checkCohesion(TableAccess mapper) throws SQLException {
        Connection conn = connectionPool.acquireConnection();

        return false;
    }

    public void createTable(TableAccess mapper) throws SQLException {
        Connection conn = connectionPool.acquireConnection();
        Statement stm = conn.createStatement();
        stm.executeUpdate(prepareCreateQuery(mapper));
    }

    public String prepareCreateQuery(TableAccess mapper){
        TableScheme scheme = mapper.getTableScheme();
        StringBuilder query = new StringBuilder("CREATE TABLE " + scheme.getName() + "(");
        var columnMap = scheme.getColumnMap();
        var key = columnMap.keySet().iterator();
        var column = columnMap.get(key.next());
        query.append(column.getName()).append(" ").append(column.getType());
        if(!column.isNullable())
            query.append(" not null");

        while(key.hasNext()){
            query.append(",\n");
            column = columnMap.get(key.next());
            query.append(column.getName()).append(" ").append(column.getType());
            if(!column.isNullable())
                query.append(" not null");
        }

        query.append(",\nprimary key(").append(mapper.getPrimaryKey().getColumnScheme().getName()).append(")");

        if(!mapper.getForeignKeys().isEmpty()){
            for(var fkey : mapper.getForeignKeys()){

                query.append(",\nforeign key(")
                        .append(fkey.getColumnScheme().getName())
                        .append(") ")
                        .append("references ")
                        .append(fkey.getReferences());
            }
        }
        query.append(");");
        System.out.println(query);
        return query.toString();
    }

    public void dropTable(TableAccess mapper) throws SQLException {
        Connection conn = connectionPool.acquireConnection();
        Statement stm = conn.createStatement();
        stm.executeUpdate("DROP TABLE " + mapper.getTableScheme().getName());
    }


}
