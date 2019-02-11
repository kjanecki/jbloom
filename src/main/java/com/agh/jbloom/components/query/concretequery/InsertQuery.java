package com.agh.jbloom.components.query.concretequery;

import com.agh.jbloom.components.mapping.ColumnScheme;
import com.agh.jbloom.components.mapping.InheritanceMapper;
import com.agh.jbloom.components.mapping.TableScheme;
import com.agh.jbloom.components.query.SqlQuery;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InsertQuery extends SqlQuery {

    public InsertQuery(InheritanceMapper inheritanceMapper, Object object) {
        super(inheritanceMapper, object);
    }

    @Override
    public String toString() {
        StringBuilder query = new StringBuilder("INSERT INTO ");

        query.append(getInheritanceMapper().getTableScheme().getName());

        query.append(" ( ");

        List<ColumnScheme> columns = new ArrayList<>(getInheritanceMapper().getTableScheme().getColumnMap().values());
        for(var column: columns){
            query.append(column.getName());
            query.append(", ");
        }

        query.delete(query.length()-2, query.length());

        query.append(" ) VALUES ( ");

        for(var column: columns){

            try {
                Object value = getInheritanceMapper().getObjectFieldAccess().getField(column.getName(), getObject());

                query.append(value);
                query.append(", ");

            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        query.delete(query.length()-2, query.length());

        query.append(" );");

        return query.toString();
    }
}
