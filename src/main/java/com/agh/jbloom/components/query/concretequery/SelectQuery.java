package com.agh.jbloom.components.query.concretequery;

import com.agh.jbloom.components.mapping.mappers.TableAccess;
import com.agh.jbloom.components.mapping.model.Key;
import com.agh.jbloom.components.query.SqlQuery;

import java.util.List;

public class SelectQuery extends SqlQuery {
    public SelectQuery(TableAccess tableAccess, Object object) {
        super(tableAccess, object);
    }

    @Override
    public String toString() {
        StringBuilder queryString = new StringBuilder("SELECT * FROM ");

        queryString.append(getTableAccess().getTableScheme().getName());


        //getTableAccess().getObjectFieldAccess().getField(column.getName(), getObject());



        List<Key> foreignKeys = getTableAccess().getForeignKeys();
        for(Key foreginKey: foreignKeys){


        }

        queryString.append(" WHERE ");

        for(var field: getObject().getClass().getDeclaredFields()){

            //TODO figure out how it should works, if there was some PK it would be great
            //TODO but if no we have to lop through all teh filed and compere them??
            String value = "test_value";

            queryString.append(field.getName());
            queryString.append('=');
            queryString.append(value);
            queryString.append(", ");

        }

        queryString.delete(queryString.length()-2, queryString.length());

        queryString.append(';');

        return queryString.toString();
    }
}
