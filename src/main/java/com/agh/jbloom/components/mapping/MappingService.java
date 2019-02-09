package com.agh.jbloom.components.mapping;

import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public interface MappingService {

    public Map<String,TableScheme> mapToTable(Class c);
}


