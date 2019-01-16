package com.agh.jbloom.components.mapping;

import java.util.Map;

public class TableScheme {

    private Map<String, ColumnScheme> columnMap;
    private String name;

    public TableScheme(Map<String, ColumnScheme> columnMap, String name) {
        this.columnMap = columnMap;
        this.name = name;
    }
}
