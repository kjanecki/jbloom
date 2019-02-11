package com.agh.jbloom.components.query.concretequeryfactory;

import com.agh.jbloom.components.mapping.mappers.TableAccess;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.concretequery.SelectQuery;
import com.agh.jbloom.components.query.SqlQuery;

public class SelectQueryFactory implements QueryFactory {
    @Override
    public SqlQuery createQuery(TableAccess tableAccess, Object obj) {
        return new SelectQuery(tableAccess, obj);
    }
}
