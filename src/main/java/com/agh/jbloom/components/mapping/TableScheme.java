package com.agh.jbloom.components.mapping;

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
}
