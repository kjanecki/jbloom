package com.agh.jbloom.components.mapping;

public class ClassTableMappingService implements MappingService {

    MapperBuilder handlerBuilder;

    public ClassTableMappingService(MapperBuilder handlerBuilder) {
        this.handlerBuilder = handlerBuilder;
    }

    @Override
    public BaseMapperHandler createMapping(BaseMapperHandler handler, Class c) {
        BaseMapperHandler newHandler = createMapping(c, handler.getSubject());
        newHandler.setParent(handler);
        return newHandler;
    }

//    public BaseMapperHandler createMapping2(Class c, Class stop) {
//        String tableName = getTableName(c);
//
//        MapperBuilder handlerBuilder =
//                new SimpleMapperBuilder(c, tableName, typeConverter);
//
//        BaseMapperHandler baseMapperHandler =
//                new BaseMapperHandler(c, handlerBuilder.withClass(c).build());
//        Class current = c;
//
//        while ((current = current.getSuperclass()) != stop){
//            InheritanceMapper mapper = handlerBuilder
//                    .withName(getTableName(current))
//                    .withSubjectClass(current)
//                    .withClass(current)
//                    .build();
//            baseMapperHandler = new BaseMapperHandler(current, baseMapperHandler, mapper);
//        }
//        return baseMapperHandler;
//    }

    @Override
    public BaseMapperHandler createMapping(Class c, Class stop) {
        if (c.equals(stop)){
            return null;
        }else{
            BaseMapperHandler parent = createMapping(c.getSuperclass(), stop);

            String tableName = getTableName(c);
            handlerBuilder.withSubjectClass(c).withName(tableName);
            InheritanceMapper mapper;

            handlerBuilder
                    .withName(getTableName(c))
                    .withSubjectClass(c)
                    .withClass(c);

            if (parent == null){
                return new BaseMapperHandler(c, handlerBuilder.build());
            }
            else {
                InheritanceMapper parentMapper = parent.getMapper();
                Key parentPrimaryKey = parentMapper.getPrimaryKey();
                String references = parentMapper.getTableScheme().getName()+"("+parentPrimaryKey.getColumnScheme().getName()+")";
                Key childPrimaryKey = new Key(parentPrimaryKey.getColumnScheme(), parentPrimaryKey.getFieldAccess(), references);
                handlerBuilder.withPrimaryKey(childPrimaryKey);
                return new BaseMapperHandler(c, parent, handlerBuilder.build());
            }
        }
    }
}
