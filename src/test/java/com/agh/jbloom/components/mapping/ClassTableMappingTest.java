package com.agh.jbloom.components.mapping;

import com.agh.jbloom.components.query.BaseSqlTypeConverter;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class ClassTableMappingTest {

    MappingService service;

    public ClassTableMappingTest() {
        this.service = new ClassTableMappingService(new SimpleMapperBuilder(new BaseSqlTypeConverter()));
    }

    @Test
    public void Hello() throws InvocationTargetException, IllegalAccessException {
        BaseMapperHandler handler = service.createMapping(SimpleEntityImpl2.class);
        System.out.println(handler);
        SimpleEntityImpl2 s = new SimpleEntityImpl2();

        handler.getMapper().getObjectFieldAccess().getField("local_param", s);
    }
}
