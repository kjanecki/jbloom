package com.agh.jbloom.components.mapping.mappers;

import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.dataaccess.IdentityField;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.Transaction;
import com.agh.jbloom.components.query.concretequeryfactory.DeleteQueryFactory;
import com.agh.jbloom.components.query.concretequeryfactory.InsertQueryFactory;

import java.sql.SQLException;
import java.util.Objects;

public class BaseInheritanceMapper implements Mapper {

    private Class subject;
    protected TableAccess tableAccess;
    private BaseInheritanceMapper parent;

    public BaseInheritanceMapper(Class subject, TableAccess tableAccess) {
        this.subject = subject;
        this.tableAccess = tableAccess;
    }

    public BaseInheritanceMapper(Class subject, BaseInheritanceMapper parent, TableAccess tableAccess) {
        this.subject = subject;
        this.parent = parent;
        this.tableAccess = tableAccess;
    }

    public void setParent(BaseInheritanceMapper parent) {
        this.parent = parent;
    }

    public TableAccess getTableAccess() {
        return tableAccess;
    }

    public BaseInheritanceMapper getParent() {
        return parent;
    }

    public Class getSubject() {
        return subject;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof BaseInheritanceMapper)) return false;
        BaseInheritanceMapper that = (BaseInheritanceMapper) object;
        return Objects.equals(getParent(), that.getParent()) &&
                Objects.equals(getSubject(), that.getSubject()) &&
                Objects.equals(getTableAccess(), that.getTableAccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParent(), getSubject(), getTableAccess());
    }

    @Override
    public String toString() {
        return "\nBaseInheritanceMapper{" +
                "subject=" + subject +
                ", tableAccess=" + tableAccess +
                ", parent=" + parent +
                '}' + '\n';
    }


//    @Override
//    public void insert(Object o, ConnectionPool connectionPool) throws SQLException {
//        Transaction transaction = new Transaction(connectionPool);
//        transaction.addQuery(insertFactory.createQuery(tableAccess, o).toString());
//        BaseInheritanceMapper next = this;
//        while((next = next.parent) != null){
//            transaction.addQuery(insertFactory.createQuery(next.tableAccess, o).toString());
//        }
//        transaction.commit();
//    }


    @Override
    public void buildTransaction(Transaction transaction, Object o, QueryFactory factory) throws SQLException {
        transaction.addQuery(factory.createQuery(tableAccess, o).toString());
        BaseInheritanceMapper next = this;
        while((next = next.parent) != null){
            transaction.addQuery(factory.createQuery(next.tableAccess, o).toString());
        }
    }

    @Override
    public Object find(IdentityField id, ConnectionPool connectionPool, QueryFactory factory) throws SQLException {
        return null;
    }
}
