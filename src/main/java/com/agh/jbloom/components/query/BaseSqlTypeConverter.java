package com.agh.jbloom.components.query;

import java.lang.reflect.Field;

public class BaseSqlTypeConverter implements SqlTypeConverter {
    @Override
    public String convert(Field f) {
        var type = f.getType().getSimpleName();
        return convert(type);
    }

    public String convert(String type){
        if(type.equals("Integer") || type.equals("int"))
            return "integer";
        else if (type.equals("String"))
            return "varchar(40)";
        else if (type.equals("Double") || type.equals("double"))
            return "double";
        else
            return "";

    }
}
