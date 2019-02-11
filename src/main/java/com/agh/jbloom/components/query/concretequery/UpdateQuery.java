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
        StringBuilder query = new StringBuilder("UPDATE ");

        query.append(getInheritanceMapper().getTableScheme().getName());

        query.append(" SET ");

        for(var column: getInheritanceMapper().getTableScheme().getColumnMap().values()){
            query.append(column.getName());
            query.append('=');
            try {
                query.append(getInheritanceMapper().getObjectFieldAccess().getField(column.getName(),getObject()));
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }

            query.append(", ");
        }

        query.delete(query.length()-2, query.length());

        query.append(';');

        return query.toString();
    }
}
