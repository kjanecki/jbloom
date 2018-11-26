package com.agh.jbloom.components;

public interface DataAccess {

    void insert(Object object);
    void delete(Object object);
    void update(Object object);
    Object find(Class c);
}
