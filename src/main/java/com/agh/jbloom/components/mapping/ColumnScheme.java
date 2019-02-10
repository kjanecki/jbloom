package com.agh.jbloom.components.mapping;

import com.agh.jbloom.annotations.Element;
import com.agh.jbloom.annotations.Nullable;
import com.agh.jbloom.components.query.SqlTypeConverter;

import java.lang.reflect.Field;
import java.util.Objects;

public class ColumnScheme {

    private String name;
    private String type;
    private boolean isNullable;

    public ColumnScheme(String name, String type, boolean isNullable) {
        this.name = name;
        this.type = type;
        this.isNullable = isNullable;
    }

    public ColumnScheme(Field field, SqlTypeConverter typeConverter){

        if(field.isAnnotationPresent(Element.class)) {
            var a = field.getAnnotation(Element.class);
            this.name = a.name();
            this.type = typeConverter.convert(field);
        }
        else {
            this.name = field.getName();
            this.type = typeConverter.convert(field);
        }

        this.isNullable = field.isAnnotationPresent(Nullable.class);
    }

    @Override
    public String toString() {
        return "ColumnScheme{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", isNullable=" + isNullable +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof ColumnScheme)) return false;
        ColumnScheme that = (ColumnScheme) object;
        return isNullable == that.isNullable &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, isNullable);
    }

    public String getName() {
        return name;
    }
}
