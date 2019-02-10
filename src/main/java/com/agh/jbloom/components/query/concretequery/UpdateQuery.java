package com.agh.jbloom.components.query.concretequery;

import com.agh.jbloom.components.mapping.TableScheme;
import com.agh.jbloom.components.query.SqlQuery;

public class UpdateQuery extends SqlQuery {

    public UpdateQuery(TableScheme tableScheme, Object object) {
        super(tableScheme, object);
    }

    @Override
    public String toString() {
        return null;
    }
}
