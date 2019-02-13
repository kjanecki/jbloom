package com.agh.jbloom.components.mapping.mappers;

import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.dataaccess.IdentityField;
import com.agh.jbloom.components.mapping.model.TableScheme;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.Transaction;
import com.agh.jbloom.components.query.concretequeryfactory.DeleteQueryFactory;
import com.agh.jbloom.components.query.concretequeryfactory.InsertQueryFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public abstract class BaseInheritanceMapper implements Mapper {

    protected Class subject;
    protected TableAccess tableAccess;

    public BaseInheritanceMapper(Class subject, TableAccess tableAccess) {
        this.subject = subject;
        this.tableAccess = tableAccess;
    }

    public TableAccess getTableAccess() {
        return tableAccess;
    }

    public Class getSubject() {
        return subject;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof BaseInheritanceMapper)) return false;
        BaseInheritanceMapper that = (BaseInheritanceMapper) object;
        return Objects.equals(getSubject(), that.getSubject()) &&
                Objects.equals(getTableAccess(), that.getTableAccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubject(), getTableAccess());
    }

    @Override
    public String toString() {
        return "BaseInheritanceMapper{" +
                "subject=" + subject +
                ", tableAccess=" + tableAccess +
                '}';
    }

    public abstract List<TableScheme> getRelatedTables();
}
