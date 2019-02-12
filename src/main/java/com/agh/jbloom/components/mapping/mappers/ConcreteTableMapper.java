package com.agh.jbloom.components.mapping.mappers;

import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.dataaccess.IdentityField;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.Transaction;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
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
    public Object find(IdentityField id, ConnectionPool connectionPool, QueryFactory factory) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Connection conn = connectionPool.acquireConnection();
        StringBuilder query = new StringBuilder("Select ");

        var columnMap = this.tableAccess.getTableScheme().getColumnMap();

        boolean start = true;
        for(var c : columnMap.keySet()){

            if(start)
                start = false;
            else
                query.append(", ");
            query.append(c);
        }
        var key = this.tableAccess.getPrimaryKey();
        query.append(" From ").append(this.tableAccess.getTableScheme().getName());
        query.append(" Where ").append(key.getColumnScheme().getName()).append(" = ").append(id.getId().toString()).append(";");
        System.out.println(query);

        ResultSet resultSet = conn.createStatement().executeQuery(query.toString());

        Object o = this.subject.getConstructor().newInstance();

        while (resultSet.next()) {
            int cnt = 1;
            for (var c : columnMap.keySet()) {
                System.out.println(resultSet.getObject(cnt).getClass());
                tableAccess.getObjectFieldAccess().setField(c,o,resultSet.getObject(cnt), resultSet.getObject(cnt).getClass());
                ++cnt;
            }
        }

        return o;
    }
}
