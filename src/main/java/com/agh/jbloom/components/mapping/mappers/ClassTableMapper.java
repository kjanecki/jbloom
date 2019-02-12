package com.agh.jbloom.components.mapping.mappers;

import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.dataaccess.IdentityField;
import com.agh.jbloom.components.dataaccess.ObjectFieldAccess;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.SelectQueryBuilder;
import com.agh.jbloom.components.query.Transaction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
    public Object find(IdentityField id, ConnectionPool connectionPool, QueryFactory factory) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        SelectQueryBuilder builder = new SelectQueryBuilder();
        List<String> fieldNames = new ArrayList<>();
        ObjectFieldAccess accessor = new ObjectFieldAccess();

        ClassTableMapper iterator = this;

        builder.withCondition(this.tableAccess.getPrimaryKey().getColumnScheme(), "=", id.getId().toString());
        var keyColumn = this.tableAccess.getPrimaryKey().getColumnScheme();
        fieldNames.add(keyColumn.getName());
        builder.withColumn(keyColumn);

        while (iterator != null){
            accessor.union(iterator.tableAccess.getObjectFieldAccess());

            var table = iterator.tableAccess.getTableScheme();
            var columns = table.getColumnMap();
            builder.withTable(table.getName());
            for(var key : columns.keySet()){
                if(!key.equals(keyColumn.getName())) {
                    fieldNames.add(key);
                    builder.withColumn(columns.get(key));
                }
            }
            iterator = (ClassTableMapper) iterator.parent;
        }

        String query = builder.build().toString();
        ResultSet resultSet = connectionPool.acquireConnection().createStatement().executeQuery(query);
        Object result = this.subject.getConstructor().newInstance();

        resultSet.next();
        for (int i = 1; i <= fieldNames.size(); ++i){
            accessor.setField(fieldNames.get(i-1),result,resultSet.getObject(i), resultSet.getObject(i).getClass());
        }

        return result;
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
