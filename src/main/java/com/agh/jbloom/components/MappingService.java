package com.agh.jbloom.components;

import com.agh.jbloom.annotations.Entity;
import com.agh.jbloom.annotations.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class MappingService {

    @Autowired
    Connection connection;

    public void registerEntity(Class c) throws SQLException {
        if(c.isAnnotationPresent(Entity.class)){
            String query = generateCreationalQuery(c);
            System.out.println(query);
            Statement stm = connection.createStatement();
            stm.executeUpdate(query);
        }
    }

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
