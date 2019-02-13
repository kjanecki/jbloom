package com.agh.jbloom.components.mapping.mappers;

import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.dataaccess.IdentityField;
import com.agh.jbloom.components.mapping.model.TableScheme;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.Transaction;

import java.sql.SQLException;
import java.util.List;

public class SingleTableMapper extends BaseInheritanceMapper {
    private TableAccess localTableAccess;


    public SingleTableMapper(Class subject, TableAccess tableAccess)
    {
        super(subject, tableAccess);
        localTableAccess =tableAccess.getIndependentCopy();
    }

    public SingleTableMapper(Class subject, TableAccess dbAccessTable, TableAccess localAccessTable)
    {
        super(subject, dbAccessTable);
        localTableAccess =localAccessTable;
    }

    public TableAccess getLocalTableAccess() {
        return localTableAccess;
    }

    @Override
    public TableAccess getTableAccess() {
        return super.getTableAccess();
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
}
