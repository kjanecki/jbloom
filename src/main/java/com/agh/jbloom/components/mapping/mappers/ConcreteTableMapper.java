package com.agh.jbloom.components.mapping.mappers;

import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.dataaccess.IdentityField;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.Transaction;

import java.sql.SQLException;

public class ConcreteTableMapper extends BaseInheritanceMapper {

    public ConcreteTableMapper(Class subject, TableAccess tableAccess) {
        super(subject, tableAccess);
    }

    public ConcreteTableMapper(Class subject, TableAccess tableAccess, BaseInheritanceMapper parent) {
        super(subject, tableAccess);
        tableAccess.union(parent.tableAccess);
    }

    @Override
    public void buildTransaction(Transaction transaction, Object o, QueryFactory factory) throws SQLException {
        transaction.addQuery(factory.createQuery(tableAccess, o).toString());
    }

    @Override
    public Object find(IdentityField id, ConnectionPool connectionPool, QueryFactory factory) throws SQLException {
        return null;
    }
}
