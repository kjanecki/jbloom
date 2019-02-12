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
        var m = service.createMapping(SimpleEntity.class);
        var m1 = service.createMapping(SimpleEntityImpl.class, m);
        var m2 = service.createMapping(SimpleEntityImpl2.class, m1);

        System.out.println(m2);
        SimpleEntityImpl2 s = new SimpleEntityImpl2();

        m2.getTableAccess().getObjectFieldAccess().getField("local_param", s);
        System.out.println();
    }
}
