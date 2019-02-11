package com.agh.jbloom.components.mapping;

import com.agh.jbloom.components.dataaccess.ObjectFieldAccess;

import java.util.List;
import java.util.Objects;

public class InheritanceMapper{

    private String mappingType;
    private TableScheme tableScheme;
    private ObjectFieldAccess objectFieldAccess;
    private Key primaryKey;
    private List<Key> foreignKeys;

    public InheritanceMapper(String mappingType, TableScheme tableScheme, ObjectFieldAccess objectFieldAccess, Key primaryKey, List<Key> foreignKeys) {
        this.mappingType = mappingType;
        this.tableScheme = tableScheme;
        this.objectFieldAccess = objectFieldAccess;
        this.primaryKey = primaryKey;
        this.foreignKeys = foreignKeys;
    }

    public void union(InheritanceMapper m2){
        this.tableScheme.union(m2.tableScheme);
        this.objectFieldAccess.union(m2.objectFieldAccess);
        this.primaryKey = m2.primaryKey;
        this.foreignKeys.addAll(m2.foreignKeys);
    }

    public TableScheme getTableScheme() {
        return tableScheme;
    }

    public String getMappingType() {
        return mappingType;
    }

    public ObjectFieldAccess getObjectFieldAccess() {
        return objectFieldAccess;
    }

    public Key getPrimaryKey() {
        return primaryKey;
    }

    public List<Key> getForeignKeys() {
        return foreignKeys;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof InheritanceMapper)) return false;
        InheritanceMapper mapper = (InheritanceMapper) object;
        return Objects.equals(mappingType, mapper.mappingType) &&
                Objects.equals(getTableScheme(), mapper.getTableScheme()) &&
                Objects.equals(objectFieldAccess, mapper.objectFieldAccess) &&
                Objects.equals(primaryKey, mapper.primaryKey) &&
                Objects.equals(foreignKeys, mapper.foreignKeys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mappingType, getTableScheme(), objectFieldAccess, primaryKey, foreignKeys);
    }

    @Override
    public String toString() {
        return "\nInheritanceMapper{" +
                "\n\tmappingType='" + mappingType + '\'' +
                ", \n\ttableScheme=" + tableScheme +
                ", \n\tobjectFieldAccess=" + objectFieldAccess +
                ", \n\tprimaryKey=" + primaryKey +
                ", \n\tforeignKeys=" + foreignKeys +
                '}';
    }
}

