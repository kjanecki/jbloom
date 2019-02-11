package com.agh.jbloom.components.query.concretequery;

import com.agh.jbloom.components.mapping.InheritanceMapper;
import com.agh.jbloom.components.mapping.TableScheme;
import com.agh.jbloom.components.query.SqlQuery;

import java.lang.reflect.InvocationTargetException;

public class UpdateQuery extends SqlQuery {

    public UpdateQuery(InheritanceMapper inheritanceMapper, Object object) {
        super(inheritanceMapper, object);
    }

    @Override
    public String toString(){
        StringBuilder query = new StringBuilder("UPDATE ")
                .append(getInheritanceMapper().getTableScheme().getName())
                .append(" SET ");

        for(var column: getInheritanceMapper().getTableScheme().getColumnMap().values()){
            query.append(column.getName())
                    .append('=');
            try {
                query.append(getInheritanceMapper().getObjectFieldAccess().getField(column.getName(),getObject()));
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }

            query.append(", ");
        }

        query.delete(query.length()-2, query.length());

        query.append(" WHERE ");

        String primary_key = getInheritanceMapper().getPrimaryKey().getColumnScheme().getName();

        try {
            query.append(primary_key)
                    .append('=')
                    .append(getInheritanceMapper().getObjectFieldAccess().getField(primary_key, getObject()));
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        query.append(';');

        return query.toString();
    }
}
