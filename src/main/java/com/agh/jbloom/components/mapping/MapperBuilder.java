package com.agh.jbloom.components.mapping;

public interface MapperBuilder {

    void clear();
    MapperBuilder withName(String tableName);
    MapperBuilder withSubjectClass(Class c);
    MapperBuilder withClass(Class c);
    MapperBuilder withPrimaryKey(Key key);
    MapperBuilder withForeignKey(Key key);
    InheritanceMapper build();
}