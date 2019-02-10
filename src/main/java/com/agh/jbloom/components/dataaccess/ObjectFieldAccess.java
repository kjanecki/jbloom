package com.agh.jbloom.components.dataaccess;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ObjectFieldAccess {

    private Map<String, Method> getters;
    private Map<String, Method> setters;

    public ObjectFieldAccess() {
        getters = new HashMap<>();
        setters = new HashMap<>();
    }

    public void addGetter(String fieldName, Method getter){
        getters.put(fieldName,getter);
    }

    public void addSetter(String fieldName, Method setter){
        setters.put(fieldName,setter);
    }

    public void setField(String fieldName, Object subject, Object value) throws InvocationTargetException, IllegalAccessException {
        Method setter;
        if ((setter = getters.get(fieldName)) != null){
            setter.invoke(subject, value);
        }
    }

    public Object getField(String fieldName, Object subject) throws InvocationTargetException, IllegalAccessException {
        Method getter;
        if ((getter = getters.get(fieldName)) != null){
            return getter.invoke(subject);
        }else{
            return null;
        }
    }

    public String generateGetterName(String fieldName){
        return "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.subSequence(1, fieldName.length());
    }

    public String generateSetterName(String fieldName){
        return "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.subSequence(1, fieldName.length());
    }

    @Override
    public String toString() {
        return "ObjectFieldAccess{" +
                "getters=" + getters +
                ", setters=" + setters +
                '}';
    }

    public void union(ObjectFieldAccess o2){
        this.setters.putAll(o2.setters);
        this.getters.putAll(o2.getters);
    }
}
