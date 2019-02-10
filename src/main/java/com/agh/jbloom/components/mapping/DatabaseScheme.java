package com.agh.jbloom.components.mapping;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DatabaseScheme {

    private Map<Class, Mapper> classHandlers;

    public DatabaseScheme() {
        classHandlers = new ConcurrentHashMap<>();
    }

    public void addHandler(Class c, Mapper h){
        classHandlers.put(c, h);
    }

    public void addHandlers(Map<Class, Mapper> map){
        classHandlers.putAll(map);
    }

    public Mapper findHandler(Class c){
        return classHandlers.get(c);
    }
}
