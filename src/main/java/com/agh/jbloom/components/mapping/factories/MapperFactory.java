package com.agh.jbloom.components.mapping.factories;

import com.agh.jbloom.annotations.Table;
import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;
import org.springframework.stereotype.Component;


@Component
public interface MapperFactory {

    BaseInheritanceMapper createMapping(Class c, BaseInheritanceMapper parent) throws IllegalAccessException;
    BaseInheritanceMapper createMapping(Class c);

//    default BaseInheritanceMapper createMapping(Class c) {
//        return createMapping(c,Object.class);
//    }

    default String getTableName(Class c){
        if(c.isAnnotationPresent(Table.class))
            return ((Table)c.getDeclaredAnnotation(Table.class)).name();
        else
            return c.getSimpleName();
    }
}


