package com.agh.jbloom.components.mapping;

import com.agh.jbloom.components.mapping.factories.ClassTableMapperFactory;
import com.agh.jbloom.components.mapping.factories.MapperFactory;
import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;
import com.agh.jbloom.components.mapping.model.SimpleTableAccessBuilder;
import com.agh.jbloom.components.query.BaseSqlTypeConverter;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class ClassTableMappingTest {

    MapperFactory service;

    public ClassTableMappingTest() {
        this.service = new ClassTableMapperFactory(new SimpleTableAccessBuilder(new BaseSqlTypeConverter()));
    }

    @Test
    public void Hello() throws InvocationTargetException, IllegalAccessException {
        BaseInheritanceMapper handler = service.createMapping(SimpleEntityImpl2.class);
        System.out.println(handler);
        SimpleEntityImpl2 s = new SimpleEntityImpl2();

        handler.getTableAccess().getObjectFieldAccess().getField("local_param", s);
    }
}
