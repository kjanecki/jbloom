package com.agh.jbloom.components.query.concretequery;

import com.agh.jbloom.components.mapping.model.ColumnScheme;
import com.agh.jbloom.components.mapping.mappers.TableAccess;
import com.agh.jbloom.components.query.SqlQuery;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class InsertQuery extends SqlQuery {

    public InsertQuery(TableAccess tableAccess, Object object) {
        super(tableAccess, object);
    }

    @Override
    public String toString() {
        StringBuilder query = new StringBuilder("INSERT INTO ");

        query.append(getTableAccess().getTableScheme().getName())
                .append(" ( ");

        List<ColumnScheme> columns = new ArrayList<>(getTableAccess().getTableScheme().getColumnMap().values());
        for(var column: columns){
            query.append(column.getName())
                    .append(", ");
        }

        query.delete(query.length()-2, query.length())
                .append(" ) VALUES ( ");

        for(var column: columns){

            try {
                Object value = getTableAccess().getObjectFieldAccess().getField(column.getName(), getObject());

                query.append("'").append(value).append("'");

            } catch (IllegalAccessException e) {
                query.append("Null ");

            }catch (InvocationTargetException e){
                e.printStackTrace();
            }

            query.append(", ");

        }

        query.delete(query.length()-2, query.length());

        query.append(" );");

        return query.toString();
    }
}
