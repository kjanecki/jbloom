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
    private DatabaseScheme databaseScheme;

    public CohesionAnalyzer(ConnectionPool connectionPool, DatabaseScheme databaseScheme) {
        this.connectionPool = connectionPool;
        this.databaseScheme = databaseScheme;
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


    public void checkCohesion(TableAccess mapper) throws SQLException {

        Connection connection = connectionPool.acquireConnection();

        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet resultSetTables = metaData.getTables(null, null, "%", null);

        Statement statement = connection.createStatement();
        ResultSet tables = statement.executeQuery("select * from information_schema.tables \n" +
                "where table_schema not in ('information_schema', 'mysql', 'performance_schema')");
        String first_table_name = "";
        if ( tables.next() ){
            first_table_name =tables.getString("TABLE_NAME");
        }

        //TODO change way of getting all users tables to sth different
        boolean flag = false;

        while (resultSetTables.next()){

            String table_name = resultSetTables.getString("TABLE_NAME");

            if (table_name.equals(first_table_name)) flag = true;

            if(flag){

                System.out.println(table_name + "=" + mapper.getTableScheme().getName());


                if ( table_name.equals(mapper.getTableScheme().getName())){

                    System.out.println("It is in DB");

                    // Its in DB so now we check if it is in cohesion of columns with DB table
                    Statement st = connection.createStatement();
                    ResultSet columns = st.executeQuery("SELECT * FROM " + table_name);
                    ResultSetMetaData columnsMetaData = columns.getMetaData();
                    int columnsCount = columnsMetaData.getColumnCount();
                    System.out.println(columnsCount);

                    // loop through all columns
                    for(var mapperColumn : mapper.getTableScheme().getColumnMap().keySet()){


                        boolean columnIsInDB = false;

                        for(int i=1; i < columnsCount + 1; ++i) {


                            if (mapperColumn.equals(columnsMetaData.getColumnName(i))) {
                                columnIsInDB = true;
                                break;
                            }
                        }

                        if (!columnIsInDB){
                            //need to add it to DB
                            System.out.println("we need to add column: " + mapperColumn);

                            //TODO need to add alter query, cant use executeQuery
                            //st.executeQuery("alter table " + table_name + " add " + mapperColumn + " varchar(255)");
                        }
                    }

                    return;
                }
            }

        }

        // It is not in DB so we add it
        System.out.println("CREATED");
        createTable(mapper);

        // 1. nowy obiekt -> dodajemy go


        // tego raczej nie ma ->  2, jezeli usunelimsy jakis obiekt -> wyjatek ze zniklal jakis obiekt


        // 3. jezeli usunelismy columne -> zostawaimy ja w db ustawiamy default na null + wyjatek(co jezeli PK (albo FK))?


        // 4. nowa kolumna -> moze byc null to wypelniamy, jezeli jest default to wypelniamy, jezeli ani to ani to to wyjatek


        // 5. jezeli zmiana typy columny -> wyjatek

    }

}
