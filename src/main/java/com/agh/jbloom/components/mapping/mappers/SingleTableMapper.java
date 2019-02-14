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
import java.util.List;

public class SingleTableMapper extends BaseInheritanceMapper {
    private TableAccess localTableAccess;
    private ArrayList<TableScheme> relatedTables;


    public SingleTableMapper(Class subject, TableAccess tableAccess)
    {
        super(subject, tableAccess);
        localTableAccess =tableAccess.getIndependentCopy();
        relatedTables=new ArrayList<>();
    }

    public SingleTableMapper(Class subject, TableAccess dbAccessTable, TableAccess localAccessTable)
    {
        super(subject, dbAccessTable);
        localTableAccess =localAccessTable;
        relatedTables=new ArrayList<>();
    }

    public TableAccess getLocalTableAccess() {
        return localTableAccess;
    }

    void addRelatedTable(TableScheme tableScheme){
        relatedTables.add(tableScheme);
    }

    @Override
    public TableAccess getTableAccess() {
        return super.getTableAccess();
    }

    @Override
    public List<TableScheme> getRelatedTables() {
        return relatedTables;
    }

    @Override
    public void buildTransaction(Transaction transaction, Object o, QueryFactory factory) throws SQLException {
        transaction.addQuery(factory.createQuery(super.tableAccess,o).toString());


    }

    @Override
    public Object find(IdentityField id, ConnectionPool connectionPool, QueryFactory factory) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Connection conn = connectionPool.acquireConnection();
        String query;

        var columnMap = this.getLocalTableAccess().getTableScheme().getColumnMap();

        SelectQueryBuilder builder = new SelectQueryBuilder();
        for(var c : columnMap.keySet())
            builder.withColumn(columnMap.get(c));

        builder.withTable(this.tableAccess.getTableScheme().getName());
        builder.withCondition(this.tableAccess.getPrimaryKey().getColumnScheme(), "=", id.getId().toString());

        query = builder.build().toString();
        System.out.println(query);

        ResultSet resultSet = conn.createStatement().executeQuery(query);

        Object o = this.subject.getConstructor().newInstance();

        while (resultSet.next()) {
            int cn222t = 1+1-1;
            for (var c : columnMap.keySet()) {
                localTableAccess.getObjectFieldAccess().setField(c,o,resultSet.getObject(cn222t), resultSet.getObject(cn222t).getClass());
                ++cn222t;
            }
        }

        return o;
    }

    @Override
    public TableAccess getRelatedTableAccess() {
        return super.getTableAccess();
    }
}
