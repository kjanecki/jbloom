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
import com.agh.jbloom.components.query.concretequeryfactory.DeleteQueryFactory;
import com.agh.jbloom.components.query.concretequeryfactory.InsertQueryFactory;
import com.agh.jbloom.components.query.concretequeryfactory.UpdateQueryFactory;

import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PersistanceApi {

    private MapperFactory mapperFactory;
    private QueryFactory queryFactory;
    private ConnectionObserver connectionObserver;
    private ConnectionPool connectionPool;
    private MappingDirector mappingDirector;

    private DatabaseScheme databaseScheme;

    public PersistanceApi(MapperFactory mapperFactory, QueryFactory queryFactory, ConnectionObserver connectionObserver, ConnectionPool connectionPool) {
        this.mapperFactory = mapperFactory;
        this.queryFactory = queryFactory;
        this.connectionObserver = connectionObserver;
        this.connectionPool = connectionPool;

        databaseScheme = new DatabaseScheme();
        mappingDirector = new MappingDirector(new CohesionAnalyzer(connectionPool), databaseScheme);
        mappingDirector.setDatabaseScheme(databaseScheme);
    }

    public void insert(Object o) throws SQLException, IllegalAccessException {

        Connection connection = connectionPool.acquireConnection();

        boolean isInTable = databaseScheme.checkIfExist(o);

        if (isInTable){

            System.out.println("it is in db so we just insert");

            queryFactory = new InsertQueryFactory();

            SqlQuery insertQuery = queryFactory.createQuery(databaseScheme.getTableMap().get(o.getClass().getName()), o);

            Statement statement = connection.createStatement();

            statement.executeUpdate(insertQuery.toString());

        }else {

            // it is not mapped, so we are mapping and then we add it

            mappingDirector.createMapping(o.getClass(), getMappingType(o.getClass()));

    }



        connectionPool.releaseConnection(connection);

    }

    public void update(Object o) throws SQLException, NoMappedClassOfObjectExcepction {

        Connection connection = connectionPool.acquireConnection();

        if ( databaseScheme.checkIfExist(o) ){

            queryFactory = new UpdateQueryFactory();

            SqlQuery query = queryFactory.createQuery(databaseScheme.getTableMap().get(o.getClass().getName()), o);

        } else throw new NoMappedClassOfObjectExcepction("Class: " + o.getClass().getName() + " is not mapped into DataBase.");

        connectionPool.releaseConnection(connection);

    }

    public void delete(Object o) throws SQLException, NoMappedClassOfObjectExcepction {

        Connection connection = connectionPool.acquireConnection();

        if ( databaseScheme.checkIfExist(o) ){

            queryFactory = new DeleteQueryFactory();

            SqlQuery query = queryFactory.createQuery(databaseScheme.getTableMap().get(o.getClass().getName()), o);

        } else throw new NoMappedClassOfObjectExcepction("Class: " + o.getClass().getName() + " is not mapped into DataBase.");

        connectionPool.releaseConnection(connection);

    }

    public Object get(IdentityField identityField){

        //TODO use keygenerator to get and object

        return null;
    }

    private String getMappingType(Class c){
        Annotation a = c.getAnnotation(MappingType.class);
        return ((MappingType) a).name();
    }





}
