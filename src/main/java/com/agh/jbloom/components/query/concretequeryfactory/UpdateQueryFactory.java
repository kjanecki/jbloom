package com.agh.jbloom.components.query.concretequeryfactory;

import com.agh.jbloom.components.mapping.mappers.TableAccess;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.SqlQuery;
import com.agh.jbloom.components.query.concretequery.UpdateQuery;

public class UpdateQueryFactory implements QueryFactory {
    @Override
    public SqlQuery createQuery(TableAccess tableAccess, Object obj) {
        return new UpdateQuery(tableAccess, obj);
    }
}
