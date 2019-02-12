package com.agh.jbloom.components.mapping.factories;

import com.agh.jbloom.annotations.Table;
import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;
import org.springframework.stereotype.Component;


@Component
public interface MapperFactory {

    BaseInheritanceMapper createMapping(Class c, BaseInheritanceMapper parent) throws InvalidArgumentException;
    BaseInheritanceMapper createMapping(Class c) throws InvalidArgumentException;

    default String getTableName(Class c){
        if(c.isAnnotationPresent(Table.class))
            return ((Table)c.getDeclaredAnnotation(Table.class)).name();
        else
            return c.getSimpleName();
    }
}


