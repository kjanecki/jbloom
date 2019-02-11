package com.agh.jbloom.components.query.concretequery;

import com.agh.jbloom.components.mapping.InheritanceMapper;
import com.agh.jbloom.components.query.SqlQuery;

public class DeleteQuery extends SqlQuery {
    public DeleteQuery(InheritanceMapper inheritanceMapper, Object object) {
        super(inheritanceMapper, object);
    }

    @Override
    public String toString() {

        String query = "";

        String primary_key = getInheritanceMapper().getPrimaryKey().getColumnScheme().getName();

        try {
            query = "DELETE FROM " +
                    getInheritanceMapper().getTableScheme().getName() +
                    " WHERE " +
                    primary_key +
                    '=' +
                    getInheritanceMapper().getObjectFieldAccess().getField(primary_key, getObject()) +
                    ';';
        }catch (Exception e){
            e.printStackTrace();
        }

        return query;
    }
}
