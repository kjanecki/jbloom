package com.agh.jbloom.components.mapping;

import com.agh.jbloom.annotations.Table;
import com.agh.jbloom.components.dataaccess.ObjectFieldAccess;
import com.agh.jbloom.components.query.SqlTypeConverter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ConcreteTableMappingService implements MappingService {

    private SqlTypeConverter typeConverter;

    public ConcreteTableMappingService(SqlTypeConverter typeConverter) {
        this.typeConverter = typeConverter;
    }

    @Override
    public MappingHandler createMapping(MappingHandler handler, Class c) {
        InheritanceMapper i2 = (InheritanceMapper)handler;
        InheritanceMapper i1 = (InheritanceMapper)createMapping(c, i2.getSubject());
        i1.union(i2);
        return i1;
    }

    private MappingHandler createMapping(Class c, Class stop) {

        String tableName = getTableName(c);

        MappingHandlerBuilder handlerBuilder =
                new ConcreteTableHandlerBuilder(c, tableName, typeConverter);

        Class current = c;
        do{
            handlerBuilder.withClass(current);
        }while ((current = current.getSuperclass()) != stop);
        return handlerBuilder.build();
    }

    @Override
    public MappingHandler createMapping(Class c) {
        return createMapping(c,Object.class);
    }

    private String getTableName(Class c){
        if(c.isAnnotationPresent(Table.class))
            return ((Table)c.getDeclaredAnnotation(Table.class)).name();
        else
            return c.getSimpleName();
    }


    class ConcreteTableHandlerBuilder implements MappingHandlerBuilder{

        ObjectFieldAccess objectFieldAccess;
        Map<String, ColumnScheme> columnSchemeMap;
        String tableName;
        SqlTypeConverter typeConverter;
        Class subject;

        public ConcreteTableHandlerBuilder(Class c, String tableName, SqlTypeConverter typeConverter) {
            this.subject = c;
            this.tableName = tableName;
            this.typeConverter = typeConverter;
            clear();
        }

        @Override
        public void clear(){
            objectFieldAccess = new ObjectFieldAccess();
            columnSchemeMap = new HashMap<>();
        }

        @Override
        public void withClass(Class c){

            Map<String, Method> methods = new HashMap<>();
            for(var m : c.getDeclaredMethods())
                methods.put(m.getName(), m);

            for(var f : c.getDeclaredFields()){
                String fieldName = f.getName();
                columnSchemeMap.put(fieldName, new ColumnScheme(f, typeConverter));
                Method m;

                if((m = methods.get(objectFieldAccess.generateGetterName(fieldName))) != null)
                    objectFieldAccess.addGetter(fieldName,m);

                if((m = methods.get(objectFieldAccess.generateSetterName(fieldName))) != null)
                    objectFieldAccess.addSetter(fieldName,m);
            }
        }

        @Override
        public MappingHandler build(){
            MappingHandler mappingHandler;
            TableScheme tableScheme = new TableScheme(columnSchemeMap, tableName);
            mappingHandler = new InheritanceMapper(subject, "ConcreteTable", tableScheme, objectFieldAccess);
            clear();
            return mappingHandler;
        }
    }
}
