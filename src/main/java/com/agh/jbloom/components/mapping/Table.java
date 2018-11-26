package com.agh.jbloom.components.mapping;

import com.agh.jbloom.components.mapping.Column;

import java.util.Map;

public class Table {

    private Map<String, Column> columnMap;
    private String name;

    public Table(Map<String, Column> columnMap, String name) {
        this.columnMap = columnMap;
        this.name = name;
    }
}
