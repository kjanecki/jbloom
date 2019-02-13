package com.agh.jbloom.components.mapping.model;

import com.agh.jbloom.annotations.Id;
import com.agh.jbloom.annotations.OneToMany;
import com.agh.jbloom.components.dataaccess.ObjectFieldAccess;
import com.agh.jbloom.components.mapping.mappers.TableAccess;
import com.agh.jbloom.components.query.SqlTypeConverter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class SimpleTableAccessBuilder implements TableAccessBuilder {

    private ObjectFieldAccess objectFieldAccess;
    private Map<String, ColumnScheme> columnSchemeMap;
    private Key primaryKey;
    private List<Key> foreignKeys;
    private String tableName;
    private SqlTypeConverter typeConverter;
    private Class subject;
    private Map<String, String> relatedClasses;

    public SimpleTableAccessBuilder(SqlTypeConverter typeConverter) {
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
        relatedClasses = new HashMap<>();
    }

    @Override
    public TableAccessBuilder withName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    @Override
    public TableAccessBuilder withSubjectClass(Class c) {
        this.subject = c;
        return this;
    }

    @Override
    public TableAccessBuilder withClass(Class c){

        Map<String, Method> methods = new HashMap<>();
        for(var m : c.getDeclaredMethods())
            methods.put(m.getName(), m);

        for(var f : c.getDeclaredFields()){
            String fieldName = f.getName();
            ColumnScheme newColumn = new ColumnScheme(f, typeConverter);

            Method getter, setter;
            if((getter = methods.get(objectFieldAccess.generateGetterName(fieldName))) != null) {
                objectFieldAccess.addGetter(fieldName,getter);
            }

            if((setter = methods.get(objectFieldAccess.generateSetterName(fieldName))) != null) {
                objectFieldAccess.addSetter(fieldName,setter);
            }

            if(f.isAnnotationPresent(OneToMany.class)){
                var typename = ((ParameterizedType)f.getGenericType()).getActualTypeArguments()[0].getTypeName().toString();
                withRelatedClass(typename,"OneToMany");
                continue;
            }

            columnSchemeMap.put(fieldName, newColumn);


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
    public TableAccessBuilder withPrimaryKey(Key key) {
        columnSchemeMap.put(key.getColumnScheme().getName(), key.getColumnScheme());
        objectFieldAccess.union(key.getFieldAccess());
        primaryKey = key;
        return this;
    }

    @Override
    public TableAccessBuilder withForeignKey(Key key) {
        columnSchemeMap.put(key.getColumnScheme().getName(), key.getColumnScheme());
        objectFieldAccess.union(key.getFieldAccess());
        foreignKeys.add(key);
        return this;
    }

    public TableAccessBuilder withRelatedClass(String classname, String relation){
        relatedClasses.put(classname,relation);
        return this;
    }

    @Override
    public TableAccess build(){
        TableAccess mapper;
        TableScheme tableScheme = new TableScheme(columnSchemeMap, tableName);
        mapper = new TableAccess("ConcreteTable", tableScheme, objectFieldAccess, primaryKey, foreignKeys,relatedClasses);
        clear();
        return mapper;
    }


}
