package com.agh.jbloom.components.query.concretequeryfactory;

import com.agh.jbloom.components.mapping.InheritanceMapper;
import com.agh.jbloom.components.mapping.TableScheme;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.SqlQuery;
import com.agh.jbloom.components.query.concretequery.InsertQuery;

public class InsertQueryFactory implements QueryFactory {
    @Override
    public SqlQuery createQuery(InheritanceMapper inheritanceMapper, Object obj) {
        return new InsertQuery(inheritanceMapper, obj);
    }
}
