package com.agh.jbloom.components.query.concretequeryfactory;

import com.agh.jbloom.components.mapping.InheritanceMapper;
import com.agh.jbloom.components.mapping.TableScheme;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.SqlQuery;
import com.agh.jbloom.components.query.concretequery.UpdateQuery;

public class UpdateQueryFactory implements QueryFactory {
    @Override
    public SqlQuery createQuery(InheritanceMapper inheritanceMapper, Object obj) {
        return new UpdateQuery(inheritanceMapper, obj);
    }
}
