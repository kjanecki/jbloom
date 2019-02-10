package com.agh.jbloom.components.query.concretequery;

import com.agh.jbloom.components.mapping.ColumnScheme;
import com.agh.jbloom.components.mapping.TableScheme;
import com.agh.jbloom.components.query.SqlQuery;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InsertQuery extends SqlQuery {

    public InsertQuery(TableScheme tableScheme, Object object) {
        super(tableScheme, object);
    }

    @Override
    public String toString() {
        StringBuilder query = new StringBuilder("INSERT INTO ");

        query.append(getTableScheme().getName());

        query.append(" ( ");

        List<ColumnScheme> columns = new ArrayList<>(getTableScheme().getColumnMap().values());
        for(var column: columns){
            query.append(column.getName());
            query.append(", ");
        }

        query.delete(query.length()-2, query.length());

        query.append(" ) VALUES ( ");

        // use DataAccess
        for(var column: columns){

            try {
                Field field = getObject().getClass().getDeclaredField(column.getName());

                field.setAccessible(true);

                Object value = field.get(getObject());

                query.append(value);
                query.append(", ");

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        query.delete(query.length()-2, query.length());

        query.append(" );");

        return query.toString();
    }
}
