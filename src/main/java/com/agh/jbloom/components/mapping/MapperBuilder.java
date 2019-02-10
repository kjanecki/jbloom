package com.agh.jbloom.components.mapping;

public interface MapperBuilder {

    void clear();
    void withClass(Class c);
    InheritanceMapper build();
}