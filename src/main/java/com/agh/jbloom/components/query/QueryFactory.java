package com.agh.jbloom.components.query;

import com.agh.jbloom.components.mapping.InheritanceMapper;
import com.agh.jbloom.components.mapping.TableScheme;

public interface QueryFactory {
    public SqlQuery createQuery(InheritanceMapper inheritanceMapper, Object obj);
}
