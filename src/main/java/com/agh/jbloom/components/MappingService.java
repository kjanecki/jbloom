package com.agh.jbloom.components;

import com.agh.jbloom.annotations.Id;
import com.agh.jbloom.model.SqlParamAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Component
public class MappingService {

    private Connection connection;
    private DataSource dataSource;
    private DatabaseMetaData databaseMetaData;

    @Autowired
    public MappingService(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
        connection = dataSource.getConnection();
        databaseMetaData = connection.getMetaData();
    }

    public void registerEntity(Class c) throws SQLException{
        if(isTableCreated(c))
            checkTableIntegration(c);
        else
            createTable(c);
    }

    public void insert(Object object) throws SQLException, NoSuchFieldException {
        String query = "insert into " + object.getClass().getSimpleName() + "(";
        for(var field : object.getClass().getDeclaredFields()) {
            query += field.getName() + ", ";
        }
        query += ")";
        query = query.replace(", )",")");
        query += " values " + ((SqlParamAdapter)object).toParamList() + ";";
        Statement stm = connection.createStatement();
        stm.executeUpdate(query);
    }

    private void checkTableIntegration(Class c) throws SQLException{
        var table = databaseMetaData.getColumns(null, null, c.getSimpleName(), null);
        Set<String> columnNames = new HashSet<>();
        while(table.next()){
            columnNames.add(table.getString("COLUMN_NAME"));
        }
        for(var field : c.getDeclaredFields()){
            if(!columnNames.contains(field.getName()))
                throw new TableNotFoundException();
        }
    }

    private boolean isTableCreated(Class c) throws SQLException {
        var tables = databaseMetaData.getTables(null, null, "%", new String[]{"TABLE"});
        while(tables.next()) {
            if(tables.getString(3).equals(c.getSimpleName()))
                return true;
        }
        return false;
    }

    private void createTable(Class c) throws SQLException {
        String query = generateCreationalQuery(c);
        System.out.println(query);
        Statement stm = connection.createStatement();
        stm.executeUpdate(query);
    }


    //    TODO: Create separate class for generating queries
    private String generateCreationalQuery(Class c){

        String query = "create table if not exists " + c.getSimpleName() + " ";
        query += generateTableDefinition(c.getDeclaredFields());
        return query;
    }

    private String generateTableDefinition(Field [] fields){
        String table = "(";
        String id = "";
        for(var field : fields){
            table += generateColumnDefinition(field) + ", ";
            if(field.isAnnotationPresent(Id.class))
                id = field.getName();

        }
        table += "primary key (" + id + ")";
        return table + ");";
    }

    private String generateColumnDefinition(Field field){
        String column = "";
        column += field.getName() + " "
                + getSqlType(field.getType().getSimpleName());
        return column;
    }

    private String getSqlType(String typename){
        if(typename.equals("int"))
            return "int(9)";
        else if (typename.equals("String"))
            return "varchar(100)";
        return "";
    }
}


class TableNotFoundException extends SQLException{

}