package com.agh.jbloom.components.query;

import com.agh.jbloom.annotations.Id;
import com.agh.jbloom.components.dataaccess.ObjectFieldAccess;
import com.agh.jbloom.components.mapping.*;
import com.agh.jbloom.components.query.concretequeryfactory.DeleteQueryFactory;
import com.agh.jbloom.components.query.concretequeryfactory.InsertQueryFactory;
import com.agh.jbloom.components.query.concretequeryfactory.SelectQueryFactory;
import com.agh.jbloom.components.query.concretequeryfactory.UpdateQueryFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class FastTesting {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Dupa dupa = new Dupa("dupka", 11);

        MappingService mappingService = new ConcreteTableMappingService(new SimpleMapperBuilder(new BaseSqlTypeConverter()));

        BaseMapperHandler bsh = mappingService.createMapping(Dupa.class);

        InheritanceMapper mapper = bsh.getMapper();



        QueryFactory query = new InsertQueryFactory();

        System.out.println(query.createQuery(mapper, dupa).toString());

    }

    static public class Dupa extends a{
        private String name;

        @Id
        private int id;

        public Dupa(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    static public class a{
        private String aa="AA";

        public String getAa() {
            return aa;
        }

        public void setAa(String aa) {
            this.aa = aa;
        }
    }
}
