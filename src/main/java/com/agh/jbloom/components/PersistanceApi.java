package com.agh.jbloom.components;

import com.agh.jbloom.components.dataaccess.ConnectionObserver;
import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.mapping.factories.MapperFactory;
import com.agh.jbloom.components.query.QueryFactory;

public class PersistanceApi {

    private MapperFactory mapperFactory;
    private QueryFactory queryFactory;
    private ConnectionObserver connectionObserver;
    private ConnectionPool connectionPool;

    public PersistanceApi(MapperFactory mapperFactory, QueryFactory queryFactory, ConnectionObserver connectionObserver, ConnectionPool connectionPool) {
        this.mapperFactory = mapperFactory;
        this.queryFactory = queryFactory;
        this.connectionObserver = connectionObserver;
        this.connectionPool = connectionPool;
    }

    public void insert(Object o){

    }

    public void update(Object o){

    }

    public void delete(Object o){

    }

    public Object find(Class c){
        return null;
    }
}
