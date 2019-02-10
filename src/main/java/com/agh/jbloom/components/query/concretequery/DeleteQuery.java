package com.agh.jbloom.components.query.concretequery;

import com.agh.jbloom.components.mapping.TableScheme;
import com.agh.jbloom.components.query.SqlQuery;

public class DeleteQuery extends SqlQuery {
    public DeleteQuery(TableScheme tableScheme, Object object) {
        super(tableScheme, object);
    }

    @Override
    public String toString() {

        //same problem here as in select
        return null;
    }
}
