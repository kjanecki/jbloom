package com.agh.jbloom.components.query;

import com.agh.jbloom.components.mapping.InheritanceMapper;
import com.agh.jbloom.components.mapping.TableScheme;

public abstract class SqlQuery {

    private InheritanceMapper inheritanceMapper;
    private Object object;

    public SqlQuery(InheritanceMapper inheritanceMapper, Object object) {
        this.inheritanceMapper = inheritanceMapper;
        this.object = object;
    }

    public InheritanceMapper getInheritanceMapper() {
        return inheritanceMapper;
    }

    public void setInheritanceMapper(InheritanceMapper inheritanceMapper) {
        this.inheritanceMapper = inheritanceMapper;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    //void add(Object obj);
    public abstract String toString();
}
