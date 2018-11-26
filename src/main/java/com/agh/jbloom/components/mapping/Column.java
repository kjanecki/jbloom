package com.agh.jbloom.components.mapping;

public class Column {

    private String name;
    private String type;
    private boolean isNullable;

    public Column(String name, String type, boolean isNullable) {
        this.name = name;
        this.type = type;
        this.isNullable = isNullable;
    }
}
