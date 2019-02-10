package com.agh.jbloom.components.query;

import com.agh.jbloom.components.mapping.TableScheme;

public interface QueryFactory {
    public SqlQuery createQuery(TableScheme tableScheme, Object obj);
}
