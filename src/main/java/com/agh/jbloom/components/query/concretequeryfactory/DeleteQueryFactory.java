package com.agh.jbloom.components.query.concretequeryfactory;

import com.agh.jbloom.components.mapping.TableScheme;
import com.agh.jbloom.components.query.concretequery.DeleteQuery;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.SqlQuery;

public class DeleteQueryFactory implements QueryFactory {

    @Override
    public SqlQuery createQuery(TableScheme tableScheme, Object obj) {
        return new DeleteQuery(tableScheme, obj);
    }
}
