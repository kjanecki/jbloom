package com.agh.jbloom.components.mapping;

public interface MapperBuilder {

    void clear();
    MapperBuilder withName(String tableName);
    MapperBuilder withSubjectClass(Class c);
    MapperBuilder withClass(Class c);
    InheritanceMapper build();
}