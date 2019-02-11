package com.agh.jbloom.components.mapping;

import com.agh.jbloom.annotations.Id;
import com.agh.jbloom.components.dataaccess.ObjectFieldAccess;
import com.agh.jbloom.components.query.SqlTypeConverter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleMapperBuilder implements MapperBuilder{

    private ObjectFieldAccess objectFieldAccess;
    private Map<String, ColumnScheme> columnSchemeMap;
    private Key primaryKey;
    private List<Key> foreignKeys;
    private String tableName;
    private SqlTypeConverter typeConverter;
    private Class subject;

    public SimpleMapperBuilder(SqlTypeConverter typeConverter) {
        this.typeConverter = typeConverter;
        clear();
    }

    @Override
    public void clear(){
        objectFieldAccess = new ObjectFieldAccess();
        columnSchemeMap = new HashMap<>();
        foreignKeys = new ArrayList<>();
        tableName = "";
        subject = null;
    }

    @Override
    public MapperBuilder withName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    @Override
    public MapperBuilder withSubjectClass(Class c) {
        this.subject = c;
        return this;
    }

    @Override
    public MapperBuilder withClass(Class c){

        Map<String, Method> methods = new HashMap<>();
        for(var m : c.getDeclaredMethods())
            methods.put(m.getName(), m);

        for(var f : c.getDeclaredFields()){
            String fieldName = f.getName();
            ColumnScheme newColumn = new ColumnScheme(f, typeConverter);

            columnSchemeMap.put(fieldName, newColumn);
            Method getter, setter;

            if((getter = methods.get(objectFieldAccess.generateGetterName(fieldName))) != null)
                objectFieldAccess.addGetter(fieldName,getter);

            if((setter = methods.get(objectFieldAccess.generateSetterName(fieldName))) != null)
                objectFieldAccess.addSetter(fieldName,setter);

            if(f.isAnnotationPresent(Id.class))
                generateKey(newColumn, getter, setter);
        }
        return this;
    }

    private void generateKey(ColumnScheme scheme, Method getter, Method setter){
        ObjectFieldAccess o = new ObjectFieldAccess();
        o.addGetter(scheme.getName(), getter);
        o.addSetter(scheme.getName(), setter);
        primaryKey = new Key(scheme, o);
    }

    @Override
    public MapperBuilder withPrimaryKey(Key key) {
        columnSchemeMap.put(key.getColumnScheme().getName(), key.getColumnScheme());
        primaryKey = key;
        return this;
    }

    @Override
    public MapperBuilder withForeignKey(Key key) {
        columnSchemeMap.put(key.getColumnScheme().getName(), key.getColumnScheme());
        foreignKeys.add(key);
        return this;
    }

    @Override
    public InheritanceMapper build(){
        InheritanceMapper mapper;
        TableScheme tableScheme = new TableScheme(columnSchemeMap, tableName);
        mapper = new InheritanceMapper("ConcreteTable", tableScheme, objectFieldAccess, primaryKey, foreignKeys);
        clear();
        return mapper;
    }
}
