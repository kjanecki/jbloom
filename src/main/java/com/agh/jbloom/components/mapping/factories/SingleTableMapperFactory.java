package com.agh.jbloom.components.mapping.factories;

import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;

public class SingleTableMapperFactory implements MapperFactory {

    @Override
    public BaseInheritanceMapper createMapping(Class c, BaseInheritanceMapper parent) throws IllegalAccessException {
        return null;
    }

    @Override
    public BaseInheritanceMapper createMapping(Class c) {
        return null;
    }
}
