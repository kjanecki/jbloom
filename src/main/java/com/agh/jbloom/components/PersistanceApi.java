package com.agh.jbloom.components;

import com.agh.jbloom.components.dataaccess.ConnectionObserver;
import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.mapping.MappingService;
import com.agh.jbloom.components.query.QueryFactory;

public class PersistanceApi {

    private MappingService mappingService;
    private QueryFactory queryFactory;
    private ConnectionObserver connectionObserver;
    private ConnectionPool connectionPool;

    public PersistanceApi(MappingService mappingService, QueryFactory queryFactory, ConnectionObserver connectionObserver, ConnectionPool connectionPool) {
        this.mappingService = mappingService;
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
