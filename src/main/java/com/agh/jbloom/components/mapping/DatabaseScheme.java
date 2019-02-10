package com.agh.jbloom.components.mapping;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DatabaseScheme {

    private Map<Class, MappingHandler> classHandlers;

    public DatabaseScheme() {
        classHandlers = new ConcurrentHashMap<>();
    }

    public void addHandler(Class c, MappingHandler h){
        classHandlers.put(c, h);
    }

    public void addHandlers(Map<Class, MappingHandler> map){
        classHandlers.putAll(map);
    }

    public MappingHandler findHandler(Class c){
        return classHandlers.get(c);
    }
}
