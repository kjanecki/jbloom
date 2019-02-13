package com.agh.jbloom.components.mapping;

import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;
import com.agh.jbloom.components.mapping.mappers.Mapper;
import com.agh.jbloom.components.mapping.mappers.TableAccess;
import com.agh.jbloom.components.mapping.model.ColumnScheme;
import com.agh.jbloom.components.mapping.model.TableScheme;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DatabaseScheme {

    private Map<Class, Mapper> classHandlers;

    private Map<String, TableAccess> tableMap;


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

        tableMap.put(mapper.getTableAccess().getTableScheme().getName(), mapper.getTableAccess());
    }

    public void deleteTable(BaseInheritanceMapper mapper){

    }

    public Map<String, TableAccess> getTableMap() {
        return tableMap;
    }

    public void setTableMap(Map<String, TableAccess> tableMap) {
        this.tableMap = tableMap;
    }

    public boolean checkIfExist(Object o){
        return classHandlers.keySet().contains(o.getClass());
    }

    public boolean checkIfExist(Class c){
        return classHandlers.keySet().contains(c);
    }
}
