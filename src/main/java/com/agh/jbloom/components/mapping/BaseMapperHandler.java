package com.agh.jbloom.components.mapping;

import java.sql.ResultSet;
import java.util.Objects;

public class BaseMapperHandler implements Mapper {

    private Class subject;
    private InheritanceMapper mapper;
    private BaseMapperHandler parent;

    public BaseMapperHandler(Class subject, InheritanceMapper mapper) {
        this.subject = subject;
        this.mapper = mapper;
    }

    public BaseMapperHandler(Class subject, BaseMapperHandler parent, InheritanceMapper mapper) {
        this.subject = subject;
        this.parent = parent;
        this.mapper = mapper;
    }

    public void setParent(BaseMapperHandler parent) {
        this.parent = parent;
    }

    public InheritanceMapper getMapper() {
        return mapper;
    }

    public BaseMapperHandler getParent() {
        return parent;
    }

    public Class getSubject() {
        return subject;
    }

    @Override
    public Object loadObject(ResultSet set) {

        return null;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof BaseMapperHandler)) return false;
        BaseMapperHandler that = (BaseMapperHandler) object;
        return Objects.equals(getParent(), that.getParent()) &&
                Objects.equals(getSubject(), that.getSubject()) &&
                Objects.equals(getMapper(), that.getMapper());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParent(), getSubject(), getMapper());
    }

    @Override
    public String toString() {
        return "\nBaseMapperHandler{" +
                "subject=" + subject +
                ", mapper=" + mapper +
                ", parent=" + parent +
                '}';
    }
}
