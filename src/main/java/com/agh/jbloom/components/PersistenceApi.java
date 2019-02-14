package com.agh.jbloom.components;

import com.agh.jbloom.annotations.MappingType;
import com.agh.jbloom.components.dataaccess.ConnectionObserver;
import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.dataaccess.IdentityField;
import com.agh.jbloom.components.dataaccess.KeyGenerator;
import com.agh.jbloom.components.exceptions.NoMappedClassOfObjectExcepction;
import com.agh.jbloom.components.mapping.CohesionAnalyzer;
import com.agh.jbloom.components.mapping.DatabaseScheme;
import com.agh.jbloom.components.mapping.MappingDirector;
import com.agh.jbloom.components.mapping.factories.MapperFactory;
import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;
import com.agh.jbloom.components.mapping.mappers.Mapper;
import com.agh.jbloom.components.query.QueryFactory;
import com.agh.jbloom.components.query.SqlQuery;
import com.agh.jbloom.components.query.Transaction;
import com.agh.jbloom.components.query.concretequeryfactory.DeleteQueryFactory;
import com.agh.jbloom.components.query.concretequeryfactory.InsertQueryFactory;
import com.agh.jbloom.components.query.concretequeryfactory.UpdateQueryFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@Component
public class PersistenceApi {

    private ConnectionObserver connectionObserver;
    private ConnectionPool connectionPool;
    private MappingDirector mappingDirector;
    private Map<String, QueryFactory> queryFactories;
    private KeyGenerator keyGenerator;
    private DatabaseScheme databaseScheme;

    public PersistenceApi(ConnectionObserver connectionObserver, ConnectionPool connectionPool, MappingDirector mappingDirector, DatabaseScheme databaseScheme) {
        this.connectionObserver = connectionObserver;
        this.connectionPool = connectionPool;
        this.mappingDirector = mappingDirector;
        this.databaseScheme = databaseScheme;

        queryFactories = new HashMap<>();
        queryFactories.put("insert", new InsertQueryFactory());
        queryFactories.put("delete", new DeleteQueryFactory());
        queryFactories.put("update", new UpdateQueryFactory());

        keyGenerator = new KeyGenerator(connectionPool);
    }

    public <T> IdentityField getKey(Class<T> c) throws SQLException {
        if (!databaseScheme.checkIfExist(c)){
            mappingDirector.createMapping2(c, c.getAnnotation(MappingType.class).name());
        }

        Class current = c;
        while (!current.getSuperclass().equals(Object.class))
            current = current.getSuperclass();

        return keyGenerator.getKey((BaseInheritanceMapper) databaseScheme.findHandler(current));
    }

    public void insert(Object o) throws SQLException{
        Mapper mapper = getMapper(o);
        executeTransaction(mapper, queryFactories.get("insert"), o);
        System.out.println("INSERT");
    }

    public void update(Object o) throws SQLException, NoMappedClassOfObjectExcepction {
        Mapper mapper = getMapper(o);
        executeTransaction(mapper, queryFactories.get("update"), o);
        System.out.println("UPDATE");
    }

    public void delete(Object o) throws SQLException, NoMappedClassOfObjectExcepction {
        Mapper mapper = getMapper(o);
        executeTransaction(mapper, queryFactories.get("delete"), o);
        System.out.println("DELETE");
    }

    private Mapper getMapper(Object o){
        return getMapper(o.getClass());
    }


    private <T> Mapper getMapper(Class<T> c){
        if (!databaseScheme.checkIfExist(c)){
            mappingDirector.createMapping2(c, c.getAnnotation(MappingType.class).name());
        }
        return databaseScheme.findHandler(c);
    }

    private void executeTransaction(Mapper mapper, QueryFactory factory, Object o) throws SQLException {
        Transaction transaction = new Transaction(connectionPool);
        mapper.buildTransaction(transaction, o, factory);
        transaction.commit();

    }

    public Object get(IdentityField identityField, Class classname){
        Mapper mapper = getMapper(classname);
        try {
            return mapper.find(identityField,connectionPool,null);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
