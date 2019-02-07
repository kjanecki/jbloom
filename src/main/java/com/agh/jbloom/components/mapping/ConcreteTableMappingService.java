package com.agh.jbloom.components.mapping;

public class ConcreteTableMappingService implements MappingService {
    @Override
    public TableScheme mapToTable(Class c) {
        return new TableScheme(null,"");
    }
}
