package com.agh.jbloom.components.mapping.mappers;

import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.dataaccess.IdentityField;
import com.agh.jbloom.components.mapping.model.TableScheme;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.SelectQueryBuilder;
import com.agh.jbloom.components.query.Transaction;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    public List<TableScheme> getRelatedTables() {
        return new ArrayList<>();
    }

    @Override
    public Object find(IdentityField id, ConnectionPool connectionPool, QueryFactory factory) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Connection conn = connectionPool.acquireConnection();
        String query;

        var columnMap = this.tableAccess.getTableScheme().getColumnMap();

        SelectQueryBuilder builder = new SelectQueryBuilder();
        for(var c : columnMap.keySet())
            builder.withColumn(columnMap.get(c));

        builder.withTable(this.tableAccess.getTableScheme().getName());
        builder.withCondition(this.tableAccess.getPrimaryKey().getColumnScheme(), "=", id.getId().toString());

        query = builder.build().toString();
        System.out.println(query);

        ResultSet resultSet = conn.createStatement().executeQuery(query.toString());

        Object o = this.subject.getConstructor().newInstance();

        while (resultSet.next()) {
            int cnt = 1;
            for (var c : columnMap.keySet()) {
                tableAccess.getObjectFieldAccess().setField(c,o,resultSet.getObject(cnt), resultSet.getObject(cnt).getClass());
                ++cnt;
            }
        }

        return o;
    }

    public <T> Collection findRelatedObjects(){

        return null;
    }

    public <T, E> void get(Class<T> c, Class<E> e) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object l =  c.cast(c.getConstructor().newInstance());
        Collection col = (Collection)l;
        System.out.println(col);
        col.add("Str");
        System.out.println(col);
    }
}
