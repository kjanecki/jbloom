package com.agh.jbloom.components.mapping.mappers;

import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.dataaccess.IdentityField;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.Transaction;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface Mapper {

    void buildTransaction(Transaction transaction, Object o, QueryFactory factory) throws SQLException;
    Object find(IdentityField id, ConnectionPool connectionPool, QueryFactory factory) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;
}
