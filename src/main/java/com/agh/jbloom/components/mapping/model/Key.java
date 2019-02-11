package com.agh.jbloom.components.mapping.model;

import com.agh.jbloom.components.dataaccess.ObjectFieldAccess;

public class Key {
    private ColumnScheme columnScheme;
    private ObjectFieldAccess fieldAccess;
    private String references;

    public Key(ColumnScheme columnScheme, ObjectFieldAccess fieldAccess) {
        this.columnScheme = columnScheme;
        this.fieldAccess = fieldAccess;
    }

    public Key(ColumnScheme columnScheme, ObjectFieldAccess fieldAccess, String references) {
        this.columnScheme = columnScheme;
        this.fieldAccess = fieldAccess;
        this.references = references;
    }

    public ColumnScheme getColumnScheme() {
        return columnScheme;
    }

    public ObjectFieldAccess getFieldAccess() {
        return fieldAccess;
    }

    public String getReferences() {
        return references;
    }

    @Override
    public String toString() {
        return "Key{" +
                "columnScheme=" + columnScheme +
                ", fieldAccess=" + fieldAccess +
                ", references='" + references + '\'' +
                '}';
    }
}
