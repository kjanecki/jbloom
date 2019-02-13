package com.agh.jbloom.components.mapping.model;

import com.agh.jbloom.annotations.Table;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TableScheme {

    private Map<String, ColumnScheme> columnMap;
    private String name;

    public TableScheme(Map<String, ColumnScheme> columnMap, String name) {
        this.columnMap = columnMap;
        this.name = name;
    }

    @Override
    public String toString() {
        return "TableScheme{" +
                "columnMap=" + columnMap +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof TableScheme)) return false;
        TableScheme that = (TableScheme) object;
        return Objects.equals(columnMap, that.columnMap) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnMap, name);
    }

    public Map<String, ColumnScheme> getColumnMap() {
        return columnMap;
    }

    public void setColumnMap(Map<String, ColumnScheme> columnMap) {
        this.columnMap = columnMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void union(TableScheme t2){
        this.columnMap.putAll(t2.columnMap);
    }

    //TODO check if this won't fuck up everything
    public TableScheme getShallowCopy() {
        Map<String,ColumnScheme> columnMapCopy=new HashMap<>(this.columnMap);
        return new TableScheme(columnMapCopy,this.name);

    }
}
