package com.agh.jbloom.components.mapping;

import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.dataaccess.IdentityField;

public class ClassTableGateway implements  InheritanceMapperGateway {

    private InheritanceMapper mapper;
    private ConnectionPool connectionPool;

    public ClassTableGateway(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void addMapper(InheritanceMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void insert(Object o) {

    }

    @Override
    public void update(Object o) {

    }

    @Override
    public void delete(Object o) {

    }

    @Override
    public Object find(IdentityField id) {
        return null;
    }
}
