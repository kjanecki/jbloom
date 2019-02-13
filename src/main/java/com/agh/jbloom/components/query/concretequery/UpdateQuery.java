package com.agh.jbloom.components.query.concretequery;

import com.agh.jbloom.components.mapping.mappers.TableAccess;
import com.agh.jbloom.components.query.SqlQuery;

import java.lang.reflect.InvocationTargetException;

public class UpdateQuery extends SqlQuery {

    public UpdateQuery(TableAccess tableAccess, Object object) {
        super(tableAccess, object);
    }

    @Override
    public String toString(){
        StringBuilder query = new StringBuilder("UPDATE ")
                .append(getTableAccess().getTableScheme().getName())
                .append(" SET ");

        for(var column: getTableAccess().getTableScheme().getColumnMap().values()){
            query.append(column.getName())
                    .append('=');
            try {
                query.append("'")
                        .append(getTableAccess().getObjectFieldAccess().getField(column.getName(),getObject()))
                        .append("'");
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }

            query.append(", ");
        }

        query.delete(query.length()-2, query.length());

        query.append(" WHERE ");

        String primary_key = getTableAccess().getPrimaryKey().getColumnScheme().getName();

        try {
            query.append(primary_key)
                    .append('=')
                    .append(getTableAccess().getObjectFieldAccess().getField(primary_key, getObject()));
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        query.append(';');

        return query.toString();
    }
}
