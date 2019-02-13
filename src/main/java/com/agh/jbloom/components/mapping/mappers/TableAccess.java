package com.agh.jbloom.components.mapping.mappers;

import com.agh.jbloom.components.dataaccess.ObjectFieldAccess;
import com.agh.jbloom.components.mapping.model.Key;
import com.agh.jbloom.components.mapping.model.TableScheme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TableAccess {

    private String mappingType;
    private TableScheme tableScheme;
    private ObjectFieldAccess objectFieldAccess;
    private Key primaryKey;
    private List<Key> foreignKeys;
    private Map<String, String> relatedClasses;

    public TableAccess(String mappingType, TableScheme tableScheme, ObjectFieldAccess objectFieldAccess, Key primaryKey, List<Key> foreignKeys, Map<String, String> relatedClasses) {
        this.mappingType = mappingType;
        this.tableScheme = tableScheme;
        this.objectFieldAccess = objectFieldAccess;
        this.primaryKey = primaryKey;
        this.foreignKeys = foreignKeys;
        this.relatedClasses = relatedClasses;
    }

    public TableAccess(String mappingType, TableScheme tableScheme, ObjectFieldAccess objectFieldAccess, Key primaryKey, List<Key> foreignKeys) {
        this.mappingType = mappingType;
        this.tableScheme = tableScheme;
        this.objectFieldAccess = objectFieldAccess;
        this.primaryKey = primaryKey;
        this.foreignKeys = foreignKeys;
        relatedClasses = new HashMap<>();
    }

    public void union(TableAccess m2){
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

    public Map<String, String> getRelatedClasses() {
        return relatedClasses;
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
        if (!(object instanceof TableAccess)) return false;
        TableAccess mapper = (TableAccess) object;
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
        return "\nTableAccess{" +
                "\n\tmappingType='" + mappingType + '\'' +
                ", \n\ttableScheme=" + tableScheme +
                ", \n\tobjectFieldAccess=" + objectFieldAccess +
                ", \n\tprimaryKey=" + primaryKey +
                ", \n\tforeignKeys=" + foreignKeys +
                '}';
    }
}

