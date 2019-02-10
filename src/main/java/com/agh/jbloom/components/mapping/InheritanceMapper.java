package com.agh.jbloom.components.mapping;

import com.agh.jbloom.components.dataaccess.ObjectFieldAccess;

public class InheritanceMapper extends BaseMapper{

    private String mappingType;
    private TableScheme tableScheme;
    private ObjectFieldAccess objectFieldAccess;

    public InheritanceMapper(Class subject, String mappingType, TableScheme tableScheme, ObjectFieldAccess objectFieldAccess) {
        super(subject);
        this.mappingType = mappingType;
        this.tableScheme = tableScheme;
        this.objectFieldAccess = objectFieldAccess;
    }

    public InheritanceMapper(Class subject, MappingHandler parent, String mappingType, TableScheme tableScheme, ObjectFieldAccess objectFieldAccess) {
        super(subject, parent);
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
}

