package com.agh.jbloom.components.mapping;

import com.agh.jbloom.annotations.Id;
import com.agh.jbloom.components.mapping.factories.*;
import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;
import com.agh.jbloom.components.mapping.model.SimpleTableAccessBuilder;
import com.agh.jbloom.components.query.BaseSqlTypeConverter;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.*;

@Component
public class MappingDirector {

    private CohesionAnalyzer cohesionAnalyzer;
    private Map<String, MapperFactory> mapperFactories;
    private DatabaseScheme databaseScheme;

    public MappingDirector(CohesionAnalyzer cohesionAnalyzer, DatabaseScheme databaseScheme) {
        this.cohesionAnalyzer = cohesionAnalyzer;
        this.databaseScheme = databaseScheme;
        this.mapperFactories = new HashMap<>();
    }

    public void createMapping2(Class c, String mappingType){
        MapperFactory factory = getMapperFactory(mappingType);
        Stack<Class> classes = this.createClassesStack(c, databaseScheme);
        try {
            createInheritanceMappers(classes, factory);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void createInheritanceMappers(Stack<Class> classes, MapperFactory factory) throws SQLException {

        BaseInheritanceMapper mapper;
        Class root = classes.pop();
        if(root.getSuperclass().equals(Object.class)){
            mapper = factory.createMapping(root);
        }else{
            mapper = factory.createMapping(root, (BaseInheritanceMapper)databaseScheme.findHandler(root.getSuperclass()));
        }
        cohesionAnalyzer.checkCohesion(mapper.getTableAccess());
        databaseScheme.addHandler(root, mapper);

        while (!classes.empty()){
            Class c = classes.pop();
            mapper = factory.createMapping(c, mapper);
            cohesionAnalyzer.checkCohesion(mapper.getTableAccess());
            databaseScheme.addHandler(c, mapper);
        }
    }

    private Stack<Class> createClassesStack(Class c, DatabaseScheme scheme) {
        Class current = c;
        Stack<Class> classes = new Stack<>();
        while (scheme.findHandler(current) == null && !current.equals(Object.class)) {
            classes.push(current);
            current = current.getSuperclass();
        }
        return classes;
    }

    public MapperFactory getMapperFactory(String mappingType){
        if(mapperFactories.containsKey(mappingType)){
            return mapperFactories.get(mappingType);
        }else {
            MapperFactory factory = null;
            switch (mappingType){
                case "SINGLE_TABLE":
                    factory = new SingleTableMapperFactory(new SimpleTableAccessBuilder(new BaseSqlTypeConverter()));
                    break;
                case "CONCRETE_TABLE":
                    factory = new ConcreteTableMapperFactory(new SimpleTableAccessBuilder(new BaseSqlTypeConverter()));
                    break;
                case "CLASS_TABLE":
                    factory = new ClassTableMapperFactory(new SimpleTableAccessBuilder(new BaseSqlTypeConverter()));
                    break;
            }
            mapperFactories.put(mappingType, factory);
            return factory;
        }

    }

    public void createMapping(Class clas, String mappingType) throws IllegalAccessException, SQLException {

        // Get all superclasses and class
        List<Class> allClasses = new ArrayList<>();
        Class tmp_class = clas;
        while (tmp_class != null) {

            allClasses.add(tmp_class);
            tmp_class = tmp_class.getSuperclass();

        }

        // Remove Object class
        allClasses.remove(allClasses.size() - 1);


        // need know which mapping create
        MapperFactory mapperFactory;

        List<BaseInheritanceMapper> mappers;

        BaseInheritanceMapper concreteMapper;
        switch (mappingType) {

            case "SINGLE_TABLE":
                mapperFactory = new SingleTableMapperFactory(new SimpleTableAccessBuilder(new BaseSqlTypeConverter()));

                mappers = new ArrayList<>();
                mappers.add(mapperFactory.createMapping(allClasses.get(allClasses.size() - 1)));

                for (int i = allClasses.size() - 2; i >= 0; --i) {
                    mappers.add(mapperFactory.createMapping(allClasses.get(i), mappers.get(mappers.size() - 1)));
                }

                //TODO rethink it (A,B,C) -> what if we add D (A,B,C,D) we have to create new table (A,B,C,D) ??
                concreteMapper = mappers.get(mappers.size() - 1);

                databaseScheme.addTable(concreteMapper);

                cohesionAnalyzer.checkCohesion(concreteMapper.getTableAccess());

                break;

            case "CONCRETE_TABLE":

                mapperFactory = new ConcreteTableMapperFactory(new SimpleTableAccessBuilder(new BaseSqlTypeConverter()));

                mappers = new ArrayList<>();
                mappers.add(mapperFactory.createMapping(allClasses.get(allClasses.size() - 1)));

                for (int i = allClasses.size() - 2; i >= 0; --i) {
                    BaseInheritanceMapper mapper = mapperFactory.createMapping(allClasses.get(i), mappers.get(mappers.size() - 1));
                    mappers.add(mapper);
                    databaseScheme.addTable(mapper);

                }

                for (var mapper : mappers) {
                    cohesionAnalyzer.checkCohesion(mapper.getTableAccess());
                }

                break;

            case "CLASS_TABLE":

                mapperFactory = new ClassTableMapperFactory(new SimpleTableAccessBuilder(new BaseSqlTypeConverter()));

                mappers = new ArrayList<>();
                mappers.add(mapperFactory.createMapping(allClasses.get(allClasses.size() - 1)));

                for (int i = allClasses.size() - 2; i >= 0; --i) {
                    BaseInheritanceMapper mapper = mapperFactory.createMapping(allClasses.get(i), mappers.get(mappers.size() - 1));
                    mappers.add(mapper);
                    databaseScheme.addTable(mapper);
                }

                for (var mapper : mappers) {
                    cohesionAnalyzer.checkCohesion(mapper.getTableAccess());
                }

                break;
        }




    }

    public DatabaseScheme getDatabaseScheme() {
        return databaseScheme;
    }

    public void setDatabaseScheme(DatabaseScheme databaseScheme) {
        this.databaseScheme = databaseScheme;
    }
}


//    public static void main(String[] args) throws IllegalAccessException, SQLException {
////        MappingDirector d = new MappingDirector();
////
////        d.createMapping(Dupa.class, "CLASS_TABLE");
//    }
//
//
//
//
//    static public class Dupa extends a {
//        private String name;
//
//        @Id
//        private int id;
//
//        public Dupa(String name, int id) {
//            this.name = name;
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//    }
//
//    static public class a extends b{
//        @Id
//        private String aa="AA";
//
//        public String getAa() {
//            return aa;
//        }
//
//        public void setAa(String aa) {
//            this.aa = aa;
//        }
//    }
//
//    static public class b{
//
//        private String bb="BB";
//
//        public String getBb() {
//            return bb;
//        }
//
//        public void setBb(String bb) {
//            this.bb = bb;
//        }
//    }
//}
