package com.agh.jbloom.components.mapping;

public class ColumnScheme {

    private String name;
    private String type;
    private boolean isNullable;

    public ColumnScheme(String name, String type, boolean isNullable) {
        this.name = name;
        this.type = type;
        this.isNullable = isNullable;
    }
}
