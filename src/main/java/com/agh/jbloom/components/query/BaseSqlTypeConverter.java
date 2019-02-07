package com.agh.jbloom.components.query;

import java.lang.reflect.Field;

public class BaseSqlTypeConverter implements SqlTypeConverter {
    @Override
    public String convert(Field f) {
        var type = f.getType();
        if(type.equals(Integer.TYPE))
            return "integer";
        else if (type.equals(String.class))
            return "varchar(40)";
        else if (type.equals(Double.TYPE))
            return "numeric(10,5)";
        else
            return "";
    }
}
