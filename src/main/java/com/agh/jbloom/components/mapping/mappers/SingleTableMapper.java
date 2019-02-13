package com.agh.jbloom.components.mapping.mappers;

import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.dataaccess.IdentityField;
import com.agh.jbloom.components.mapping.model.TableScheme;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.Transaction;

import java.sql.SQLException;
import java.util.List;

public class SingleTableMapper extends BaseInheritanceMapper {
    private TableAccess DBTableAccess;


    public SingleTableMapper(Class subject, TableAccess tableAccess)
    {
        super(subject, tableAccess);
        DBTableAccess =tableAccess.getIndependentCopy();
    }

    public SingleTableMapper(Class subject, TableAccess tableAccess, TableAccess dbAccesTable)
    {
        super(subject, tableAccess);
        DBTableAccess =dbAccesTable;
    }

    public TableAccess getDBTableAccess() {
        return DBTableAccess;
    }

    @Override
    public List<TableScheme> getRelatedTables() {
        return null;
    }

    @Override
    public void buildTransaction(Transaction transaction, Object o, QueryFactory factory) throws SQLException {

    }

    @Override
    public Object find(IdentityField id, ConnectionPool connectionPool, QueryFactory factory) throws SQLException {
        return null;
    }

    @Override
    public TableAccess getRelatedTableAccess() {
        return tableAccess;
    }
}
