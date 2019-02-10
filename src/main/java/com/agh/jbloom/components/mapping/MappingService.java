package com.agh.jbloom.components.mapping;

import com.agh.jbloom.annotations.Table;
import org.springframework.stereotype.Component;


@Component
public interface MappingService {

    BaseMapperHandler createMapping(BaseMapperHandler handler, Class c);
    BaseMapperHandler createMapping(Class c, Class stop);

    default BaseMapperHandler createMapping(Class c) {
        return createMapping(c,Object.class);
    }

    default String getTableName(Class c){
        if(c.isAnnotationPresent(Table.class))
            return ((Table)c.getDeclaredAnnotation(Table.class)).name();
        else
            return c.getSimpleName();
    }
}


