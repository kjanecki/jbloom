package com.agh.jbloom.components.mapping;

public interface MappingHandlerBuilder {

    void clear();
    void withClass(Class c);
    MappingHandler build();
}