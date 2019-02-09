package com.agh.jbloom.components.mapping;

import com.agh.jbloom.annotations.Table;
import com.agh.jbloom.components.query.SqlTypeConverter;

import java.util.HashMap;
import java.util.Map;

public class ConcreteTableMappingService implements MappingService {

    SqlTypeConverter typeConverter;

    public ConcreteTableMappingService(SqlTypeConverter typeConverter) {
        this.typeConverter = typeConverter;
    }

    @Override
    public TableScheme mapToTable(Class c) {
        var columns = generateColumnMap(c);
        var tableName = getTableName(c);
        return new TableScheme(columns, tableName);
    }

    private Map<String, ColumnScheme> generateColumnMap(Class c){
        Map<String, ColumnScheme> columns = new HashMap<>();
        Class current = c;

        do{
            columns.putAll(generateClassColumnMap(current));
        }while ((current = current.getSuperclass()) != null);

        return columns;
    }

    private Map<String, ColumnScheme> generateClassColumnMap(Class c){
        Map<String, ColumnScheme> columns = new HashMap<>();
        for(var f : c.getDeclaredFields()){
            columns.put(f.getName(), new ColumnScheme(f, typeConverter));
        }
        return columns;
    }

    private String getTableName(Class c){
        if(c.isAnnotationPresent(Table.class))
            return ((Table)c.getDeclaredAnnotation(Table.class)).name();
        else
            return c.getSimpleName();
    }
}
