package com.agh.jbloom.components.query.concretequeryfactory;

import com.agh.jbloom.components.mapping.InheritanceMapper;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.concretequery.SelectQuery;
import com.agh.jbloom.components.query.SqlQuery;

public class SelectQueryFactory implements QueryFactory {
    @Override
    public SqlQuery createQuery(InheritanceMapper inheritanceMapper, Object obj) {
        return new SelectQuery(inheritanceMapper, obj);
    }
}
