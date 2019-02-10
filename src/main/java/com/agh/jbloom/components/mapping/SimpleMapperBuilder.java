package com.agh.jbloom.components.mapping;

import com.agh.jbloom.components.dataaccess.ObjectFieldAccess;
import com.agh.jbloom.components.query.SqlTypeConverter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SimpleMapperBuilder implements MapperBuilder{

    private ObjectFieldAccess objectFieldAccess;
    private Map<String, ColumnScheme> columnSchemeMap;
    private String tableName;
    private SqlTypeConverter typeConverter;
    private Class subject;

    public SimpleMapperBuilder(Class c, String tableName, SqlTypeConverter typeConverter) {
        this.subject = c;
        this.tableName = tableName;
        this.typeConverter = typeConverter;
        clear();
    }

    @Override
    public void clear(){
        objectFieldAccess = new ObjectFieldAccess();
        columnSchemeMap = new HashMap<>();
    }

    @Override
    public void withClass(Class c){

        Map<String, Method> methods = new HashMap<>();
        for(var m : c.getDeclaredMethods())
            methods.put(m.getName(), m);

        for(var f : c.getDeclaredFields()){
            String fieldName = f.getName();
            columnSchemeMap.put(fieldName, new ColumnScheme(f, typeConverter));
            Method m;

            if((m = methods.get(objectFieldAccess.generateGetterName(fieldName))) != null)
                objectFieldAccess.addGetter(fieldName,m);

            if((m = methods.get(objectFieldAccess.generateSetterName(fieldName))) != null)
                objectFieldAccess.addSetter(fieldName,m);
        }
    }

    @Override
    public InheritanceMapper build(){
        InheritanceMapper mapper;
        TableScheme tableScheme = new TableScheme(columnSchemeMap, tableName);
        mapper = new InheritanceMapper("ConcreteTable", tableScheme, objectFieldAccess);
        clear();
        return mapper;
    }
}
