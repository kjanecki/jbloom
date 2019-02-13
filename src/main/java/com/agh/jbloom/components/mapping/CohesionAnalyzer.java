package com.agh.jbloom.components.mapping;

import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.dataaccess.DataSource;
import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;
import com.agh.jbloom.components.mapping.mappers.ConcreteTableMapper;
import com.agh.jbloom.components.mapping.mappers.TableAccess;
import com.agh.jbloom.components.mapping.model.TableScheme;

import java.sql.*;

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


    public boolean chechCohesion(DatabaseScheme databaseScheme, BaseInheritanceMapper mapper) throws SQLException {

        Connection connection = connectionPool.acquireConnection();

        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet tables = metaData.getTables(null, null, null, null);


        while (tables.next()){
            System.out.println(tables.getString(3)); // 3 stands for table name
        }

        // 1. nowy obiekt -> dodajemy go


        // 2, jezeli usunelimsy jakis obiekt -> wyjatek ze zniklal jakis obiekt


        // 3. jezeli usunelismy columne -> zostawaimy ja w db ustawiamy default na null + wyjatek(co jezeli PK (albo FK))?


        // 4. nowa kolumna -> moze byc null to wypelniamy, jezeli jest default to wypelniamy, jezeli ani to ani to to wyjatek


        // 5. jezeli zmiana typy columny -> wyjatek

        return true;
    }

    public static void main(String[] args) {
    }

}
