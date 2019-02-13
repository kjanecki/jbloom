package com.agh.jbloom.components;

import com.agh.jbloom.annotations.MappingType;
import com.agh.jbloom.components.dataaccess.ConnectionObserver;
import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.dataaccess.IdentityField;
import com.agh.jbloom.components.exceptions.NoMappedClassOfObjectExcepction;
import com.agh.jbloom.components.mapping.CohesionAnalyzer;
import com.agh.jbloom.components.mapping.DatabaseScheme;
import com.agh.jbloom.components.mapping.MappingDirector;
import com.agh.jbloom.components.mapping.factories.MapperFactory;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.SqlQuery;
import com.agh.jbloom.components.query.Transaction;
import com.agh.jbloom.components.query.concretequeryfactory.DeleteQueryFactory;
import com.agh.jbloom.components.query.concretequeryfactory.InsertQueryFactory;
import com.agh.jbloom.components.query.concretequeryfactory.UpdateQueryFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PersistanceApi {

    private ConnectionObserver connectionObserver;
    private ConnectionPool connectionPool;
    private MappingDirector mappingDirector;

    private DatabaseScheme databaseScheme;

    public PersistanceApi(ConnectionObserver connectionObserver, ConnectionPool connectionPool) {
        this.connectionObserver = connectionObserver;
        this.connectionPool = connectionPool;

        databaseScheme = new DatabaseScheme();
        mappingDirector = new MappingDirector(new CohesionAnalyzer(connectionPool, databaseScheme),databaseScheme);
        mappingDirector.setDatabaseScheme(databaseScheme);
    }

    public void insert(Object o) throws SQLException {

        boolean isInTable = databaseScheme.checkIfExist(o);
        if (!isInTable){
            mappingDirector.createMapping2(o.getClass(), o.getClass().getAnnotation(MappingType.class).name());
        }

        Transaction transaction = new Transaction(connectionPool);
        databaseScheme.findHandler(o.getClass()).buildTransaction(transaction, o, new InsertQueryFactory());
        System.out.println("INSERT");
    }

//    public void update(Object o) throws SQLException, NoMappedClassOfObjectExcepction {
//
//        Connection connection = connectionPool.acquireConnection();
//
//        if ( databaseScheme.checkIfExist(o) ){
//
//            queryFactory = new UpdateQueryFactory();
//
//            SqlQuery query = queryFactory.createQuery(databaseScheme.getTableMap().get(o.getClass().getName()), o);
//
//        } else throw new NoMappedClassOfObjectExcepction("Class: " + o.getClass().getName() + " is not mapped into DataBase.");
//
//        connectionPool.releaseConnection(connection);
//
//    }

//    public void delete(Object o) throws SQLException, NoMappedClassOfObjectExcepction {
//
//        Connection connection = connectionPool.acquireConnection();
//
//        if ( databaseScheme.checkIfExist(o) ){
//
//            queryFactory = new DeleteQueryFactory();
//
//            SqlQuery query = queryFactory.createQuery(databaseScheme.getTableMap().get(o.getClass().getName()), o);
//
//        } else throw new NoMappedClassOfObjectExcepction("Class: " + o.getClass().getName() + " is not mapped into DataBase.");
//
//        connectionPool.releaseConnection(connection);
//
//    }
//
//    public Object get(IdentityField identityField){
//
//        //TODO use keygenerator to get and object
//
//        return null;
//    }


}
