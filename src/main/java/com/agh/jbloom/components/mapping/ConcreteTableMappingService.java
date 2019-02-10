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
    public BaseMapperHandler createMapping(BaseMapperHandler handler, Class c) {
        BaseMapperHandler newHandler = createMapping(c, handler.getSubject());
        newHandler.getMapper().union(handler.getMapper());
        return newHandler;
    }

    @Override
    public BaseMapperHandler createMapping(Class c, Class stop) {

        String tableName = getTableName(c);

        MapperBuilder handlerBuilder =
                new SimpleMapperBuilder(c, tableName, typeConverter);

        Class current = c;
        do{
            handlerBuilder.withClass(current);
        }while ((current = current.getSuperclass()) != stop);
        return new BaseMapperHandler(c, handlerBuilder.build());
    }
}
