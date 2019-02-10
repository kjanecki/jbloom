package com.agh.jbloom.components.mapping;

import com.agh.jbloom.components.query.SqlTypeConverter;

public class ClassTableMappingService implements MappingService {

    SqlTypeConverter typeConverter;

    public ClassTableMappingService(SqlTypeConverter typeConverter) {
        this.typeConverter = typeConverter;
    }

    @Override
    public BaseMapperHandler createMapping(BaseMapperHandler handler, Class c) {
        BaseMapperHandler newHandler = createMapping(c, handler.getSubject());
        newHandler.setParent(handler);
        return newHandler;
    }

    @Override
    public BaseMapperHandler createMapping(Class c, Class stop) {
        String tableName = getTableName(c);

        MapperBuilder handlerBuilder =
                new SimpleMapperBuilder(c, tableName, typeConverter);

        BaseMapperHandler baseMapperHandler =
                new BaseMapperHandler(c, handlerBuilder.withClass(c).build());
        Class current = c;

        while ((current = current.getSuperclass()) != stop){
            InheritanceMapper mapper = handlerBuilder
                    .withName(getTableName(current))
                    .withSubjectClass(current)
                    .withClass(current)
                    .build();
            baseMapperHandler = new BaseMapperHandler(current, baseMapperHandler, mapper);
        }
        return baseMapperHandler;
    }
}
