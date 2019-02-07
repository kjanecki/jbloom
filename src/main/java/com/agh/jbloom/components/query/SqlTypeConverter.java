package com.agh.jbloom.components.query;

import java.lang.reflect.Field;

public interface SqlTypeConverter {

    String convert(Field f);
}
