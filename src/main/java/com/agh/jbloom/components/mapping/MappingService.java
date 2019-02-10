package com.agh.jbloom.components.mapping;

import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public interface MappingService {

    MappingHandler createMapping(MappingHandler handler, Class c);
    MappingHandler createMapping(Class c);
}


