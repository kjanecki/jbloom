package com.agh.jbloom.components.query.concretequeryfactory;

import com.agh.jbloom.components.mapping.mappers.TableAccess;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.SqlQuery;
import com.agh.jbloom.components.query.concretequery.InsertQuery;

public class InsertQueryFactory implements QueryFactory {
    @Override
    public SqlQuery createQuery(TableAccess tableAccess, Object obj) {
        return new InsertQuery(tableAccess, obj);
    }
}
