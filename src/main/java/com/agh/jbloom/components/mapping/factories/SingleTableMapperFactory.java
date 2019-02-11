package com.agh.jbloom.components.mapping.factories;

import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;

public class SingleTableMapperFactory implements MapperFactory {

    @Override
    public BaseInheritanceMapper createMapping(BaseInheritanceMapper handler, Class c) {
        return null;
    }

    @Override
    public BaseInheritanceMapper createMapping(Class c, Class stop) {
        return null;
    }
}
