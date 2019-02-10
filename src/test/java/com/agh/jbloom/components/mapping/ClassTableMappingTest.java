package com.agh.jbloom.components.mapping;

import com.agh.jbloom.components.query.BaseSqlTypeConverter;
import org.junit.Test;

public class ClassTableMappingTest {

    MappingService service;

    public ClassTableMappingTest() {
        this.service = new ClassTableMappingService(new BaseSqlTypeConverter());
    }

    @Test
    public void Hello() {
        BaseMapperHandler handler = service.createMapping(SimpleEntityImpl2.class);
        System.out.println(handler);
    }
}
