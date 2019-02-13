package com.agh.jbloom.components;

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
        mappingDirector = new MappingDirector(new CohesionAnalyzer(connectionPool, databaseScheme));
    }

    public void insert(Object o) throws SQLException {

        Connection connection = connectionPool.acquireConnection();

        //TODO how to check if object class is already mapped
        boolean isInTabele = databaseScheme.checkIfExist(o);

        if (isInTabele){

            queryFactory = new InsertQueryFactory();

            //TODO need to get table access
            SqlQuery insertQuery = queryFactory.createQuery(null, o);

            Statement statement = connection.createStatement();

            statement.executeUpdate(insertQuery.toString());

        }else {

            // it is not mapped, so we are mapping and then we add it
            mapClassIntoDB(o.getClass());


        }



        connectionPool.releaseConnection(connection);

    }

    private void mapClassIntoDB(Class clas){

        //TODO
    }

    public void update(Object o) throws SQLException, NoMappedClassOfObjectExcepction {

        Connection connection = connectionPool.acquireConnection();

        if ( databaseScheme.checkIfExist(o) ){

            queryFactory = new UpdateQueryFactory();

            //TODO have to know how to get tableAccess base on class of an object
            SqlQuery query = queryFactory.createQuery(null, o);

        } else throw new NoMappedClassOfObjectExcepction("Class: " + o.getClass().getName() + " is not mapped into DataBase.");

        connectionPool.releaseConnection(connection);

    }

    public void delete(Object o) throws SQLException, NoMappedClassOfObjectExcepction {

        Connection connection = connectionPool.acquireConnection();

        if ( databaseScheme.checkIfExist(o) ){

            queryFactory = new DeleteQueryFactory();

            //TODO have to know how to get tableAccess base on class of an object
            SqlQuery query = queryFactory.createQuery(null, o);

        } else throw new NoMappedClassOfObjectExcepction("Class: " + o.getClass().getName() + " is not mapped into DataBase.");

        connectionPool.releaseConnection(connection);

    }

    public Object get(IdentityField identityField){

        //TODO use keygenerator to get and object

        return null;
    }


}
