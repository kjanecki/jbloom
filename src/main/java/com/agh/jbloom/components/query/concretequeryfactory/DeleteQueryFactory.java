package com.agh.jbloom.components.query.concretequeryfactory;

import com.agh.jbloom.components.mapping.mappers.TableAccess;
import com.agh.jbloom.components.query.concretequery.DeleteQuery;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.SqlQuery;

public class DeleteQueryFactory implements QueryFactory {

    @Override
    public SqlQuery createQuery(TableAccess tableAccess, Object obj) {
        return new DeleteQuery(tableAccess, obj);
    }
}
