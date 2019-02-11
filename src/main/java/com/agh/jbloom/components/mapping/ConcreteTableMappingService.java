package com.agh.jbloom.components.mapping;

public class ConcreteTableMappingService implements MappingService {

    private MapperBuilder handlerBuilder;

    public ConcreteTableMappingService(MapperBuilder handlerBuilder) {
        this.handlerBuilder = handlerBuilder;
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
        handlerBuilder.withSubjectClass(c).withName(tableName);

        Class current = c;
        do{
            handlerBuilder.withClass(current);
        }while ((current = current.getSuperclass()) != stop);
        return new BaseMapperHandler(c, handlerBuilder.build());
    }
}
