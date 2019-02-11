package com.agh.jbloom.components.mapping;

import com.agh.jbloom.components.dataaccess.IdentityField;

public interface InheritanceMapperGateway {

    void addMapper(InheritanceMapper mapper);

    void insert(Object o);
    void update(Object o);
    void delete(Object o);
    Object find(IdentityField id);
}
