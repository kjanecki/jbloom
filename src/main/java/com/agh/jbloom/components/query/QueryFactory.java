package com.agh.jbloom.components.query;

import com.agh.jbloom.components.mapping.mappers.TableAccess;

public interface QueryFactory {
    public SqlQuery createQuery(TableAccess tableAccess, Object obj);
}
