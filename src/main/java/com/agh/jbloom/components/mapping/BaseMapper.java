package com.agh.jbloom.components.mapping;

import java.sql.ResultSet;

public abstract class BaseMapper implements MappingHandler {

    private MappingHandler parent;
    private Class subject;

    public BaseMapper(Class subject) {
        this.subject = subject;
    }

    public BaseMapper(Class subject, MappingHandler parent) {
        this.subject = subject;
        this.parent = parent;
    }

    public MappingHandler getParent() {
        return parent;
    }

    public Class getSubject() {
        return subject;
    }

    @Override
    public Object loadObject(ResultSet set) {

        return null;
    }
}
