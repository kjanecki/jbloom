package com.agh.jbloom.components.query.concretequery;

import com.agh.jbloom.components.mapping.mappers.TableAccess;
import com.agh.jbloom.components.query.SqlQuery;

public class DeleteQuery extends SqlQuery {
    public DeleteQuery(TableAccess tableAccess, Object object) {
        super(tableAccess, object);
    }

    @Override
    public String toString() {

        String query = "";

        String primary_key = getTableAccess().getPrimaryKey().getColumnScheme().getName();

        try {
            query = "DELETE FROM " +
                    getTableAccess().getTableScheme().getName() +
                    " WHERE " +
                    primary_key +
                    '=' +
                    getTableAccess().getObjectFieldAccess().getField(primary_key, getObject()) +
                    ';';
        }catch (Exception e){
            e.printStackTrace();
        }

        return query;
    }
}
