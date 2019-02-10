package com.agh.jbloom.components.mapping;

import com.agh.jbloom.components.dataaccess.ObjectFieldAccess;

import java.util.Objects;

public class InheritanceMapper{

    private String mappingType;
    private TableScheme tableScheme;
    private ObjectFieldAccess objectFieldAccess;

    public InheritanceMapper(String mappingType, TableScheme tableScheme, ObjectFieldAccess objectFieldAccess) {
        this.mappingType = mappingType;
        this.tableScheme = tableScheme;
        this.objectFieldAccess = objectFieldAccess;
    }

    public void union(InheritanceMapper m2){
        this.tableScheme.union(m2.tableScheme);
        this.objectFieldAccess.union(m2.objectFieldAccess);
    }

    public TableScheme getTableScheme() {
        return tableScheme;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof InheritanceMapper)) return false;
        InheritanceMapper that = (InheritanceMapper) object;
        return Objects.equals(mappingType, that.mappingType) &&
                Objects.equals(getTableScheme(), that.getTableScheme()) &&
                Objects.equals(objectFieldAccess, that.objectFieldAccess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mappingType, getTableScheme(), objectFieldAccess);
    }

    @Override
    public String toString() {
        return "InheritanceMapper{" +
                "mappingType='" + mappingType + '\'' +
                ", tableScheme=" + tableScheme +
                ", objectFieldAccess=" + objectFieldAccess +
                '}';
    }
}

