package com.agh.jbloom.components.mapping;

import com.agh.jbloom.annotations.Id;
import com.agh.jbloom.components.dataaccess.ObjectFieldAccess;
import com.agh.jbloom.components.mapping.factories.ClassTableMapperFactory;
import com.agh.jbloom.components.mapping.factories.MapperFactory;
import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;
import com.agh.jbloom.components.mapping.model.SimpleTableAccessBuilder;
import com.agh.jbloom.components.query.BaseSqlTypeConverter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MappingDirector {


    public void createMapping(Class clas) throws IllegalAccessException {

        // need know which mapping create
        MapperFactory service = new ClassTableMapperFactory(new SimpleTableAccessBuilder(new BaseSqlTypeConverter()));

        BaseInheritanceMapper mapper = service.createMapping(clas);


        // Get all superclass and class
        List<Class> allClasses = new ArrayList<>();
        Class tmp_class = clas;
        while (tmp_class != null) {

            allClasses.add(tmp_class);

            tmp_class = tmp_class.getSuperclass();

        }

        // Remove Object class
        allClasses.remove(allClasses.size() - 1);

        List<BaseInheritanceMapper> mappers = new ArrayList<>();
        mappers.add(service.createMapping(allClasses.get(allClasses.size() - 1)));

        for (int i = allClasses.size() - 2; i >= 0; --i) {
            mappers.add(service.createMapping(allClasses.get(i), mappers.get(mappers.size() - 1)));
        }

        //BaseInheritanceMapper concreteMapper = mappers.get(mappers.size() - 1);


    }
}


//    public static void main(String[] args) throws IllegalAccessException {
//        MappingDirector d = new MappingDirector();
//
//        d.createMapping(Dupa.class);
//    }
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
