package com.agh.jbloom.components.mapping.mappers;

import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.dataaccess.IdentityField;
import com.agh.jbloom.components.mapping.DatabaseScheme;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.Transaction;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class ForeignKeyMapper implements  Mapper{

    private BaseInheritanceMapper relatedMapper;
    private DatabaseScheme databaseScheme;

    @Override
    public void buildTransaction(Transaction transaction, Object o, QueryFactory factory) throws SQLException {

    }

    @Override
    public Object find(IdentityField id, ConnectionPool connectionPool, QueryFactory factory) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return null;
    }
}
