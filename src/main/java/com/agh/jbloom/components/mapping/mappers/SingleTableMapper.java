package com.agh.jbloom.components.mapping.mappers;

import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.dataaccess.IdentityField;
import com.agh.jbloom.components.mapping.model.TableScheme;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.Transaction;

import java.sql.SQLException;
import java.util.List;

public class SingleTableMapper extends BaseInheritanceMapper {


    public SingleTableMapper(Class subject, TableAccess tableAccess) {
        super(subject, tableAccess);
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
