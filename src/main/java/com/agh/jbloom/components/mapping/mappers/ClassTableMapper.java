package com.agh.jbloom.components.mapping.mappers;

import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.dataaccess.IdentityField;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.Transaction;

import java.sql.SQLException;

public class ClassTableMapper extends BaseInheritanceMapper{

    private BaseInheritanceMapper parent;

    public ClassTableMapper(Class subject, TableAccess tableAccess) {
        super(subject, tableAccess);
    }

    public ClassTableMapper(Class subject, TableAccess tableAccess, BaseInheritanceMapper parent) {
        super(subject, tableAccess);
        this.parent = parent;
    }

    @Override
    public void buildTransaction(Transaction transaction, Object o, QueryFactory factory) throws SQLException {

        BaseInheritanceMapper iterator = this;
        while (iterator != null){
            transaction.addQuery(factory.createQuery(iterator.tableAccess, o).toString());
            iterator = ((ClassTableMapper) iterator).parent;
        }
    }

    @Override
    public Object find(IdentityField id, ConnectionPool connectionPool, QueryFactory factory) throws SQLException {
        return null;
    }

    @Override
    public String toString() {
        return "ClassTableMapper{" +
                "\nsubject=" + subject +
                ",\ntableAccess=" + tableAccess +
                ",\nparent=" + parent +
                '}';
    }
}
