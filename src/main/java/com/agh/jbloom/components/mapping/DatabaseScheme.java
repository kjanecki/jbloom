package com.agh.jbloom.components.mapping;

import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;
import com.agh.jbloom.components.mapping.mappers.Mapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
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

    public void addTable(BaseInheritanceMapper mapper){

    }

    public void deleteTable(BaseInheritanceMapper mapper){

    }

    public boolean checkIfExist(BaseInheritanceMapper mapper){

        return false;
    }
}
