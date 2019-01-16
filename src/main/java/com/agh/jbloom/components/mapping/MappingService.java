package com.agh.jbloom.components.mapping;

import org.springframework.stereotype.Component;


@Component
public interface MappingService {

    public TableScheme mapToTable(Class c);
}


