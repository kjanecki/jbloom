package com.agh.jbloom.components.mapping;

import com.agh.jbloom.annotations.Entity;
import com.agh.jbloom.annotations.Id;
import com.agh.jbloom.annotations.Table;
import com.agh.jbloom.components.query.BaseSqlTypeConverter;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@TestComponent
@Entity
@Table(name = "simple_entity")
class SimpleEntity{

    @Id
    private int id;
    private String name;
    private String description;

    public SimpleEntity() {
    }

    public SimpleEntity(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

@TestComponent
@Entity
@Table(name = "simple_entity_impl")
class SimpleEntityImpl extends  SimpleEntity {

    private Double param;

    public SimpleEntityImpl() {
    }

    public SimpleEntityImpl(int id, String name, String description, Double param) {
        super(id, name, description);
        this.param = param;
    }

    public Double getParam() {
        return param;
    }

    public void setParam(Double param) {
        this.param = param;
    }
}

@TestComponent
@Entity
@Table(name = "simple_entity_impl2")
class SimpleEntityImpl2 extends SimpleEntityImpl{

    private String local_param;

    public SimpleEntityImpl2() {
    }

    public SimpleEntityImpl2(int id, String name, String description, Double param, String local_param) {
        super(id, name, description, param);
        this.local_param = local_param;
    }

    public String getLocal_param() {
        return local_param;
    }

    public void setLocal_param(String local_param) {
        this.local_param = local_param;
    }
}



public class ConcreteTableMappingTest {

    private MappingService mappingService;
    Map<String, ColumnScheme> base;

    public ConcreteTableMappingTest() {
        mappingService = new ConcreteTableMappingService(new BaseSqlTypeConverter());
        initializeBaseTableScheme();
    }

    void initializeBaseTableScheme(){
        base = new HashMap<>();
        base.put("id", new ColumnScheme("id", "integer", false));
        base.put("name", new ColumnScheme("name", "varchar(40)", false));
        base.put("description", new ColumnScheme("description", "varchar(40)", false));
    }


    @Test
    public void canCreateMappingForSingleClass() throws NoSuchFieldException, IllegalAccessException {
        TableScheme table = new TableScheme(base, "simple_entity");
        MappingHandler mappingHandler = mappingService.createMapping(SimpleEntity.class);
        Field f = InheritanceMapper.class.getDeclaredField("tableScheme");
        f.setAccessible(true);
        assertEquals(table,f.get(mappingHandler));
    }

    @Test
    public void canCreateMappingForDerivedClasses() throws NoSuchFieldException, IllegalAccessException {
        Map<String, ColumnScheme> columnMap = new HashMap<>();
        columnMap.put("param", new ColumnScheme("param", "numeric(10,5)", false));
        columnMap.putAll(base);
        TableScheme table1 = new TableScheme(columnMap, "simple_entity_impl");
        MappingHandler m = mappingService.createMapping(SimpleEntityImpl.class);
        assertEquals(table1, ((InheritanceMapper)m).getTableScheme());

        Map<String, ColumnScheme> columnMap2 = new HashMap<>();
        columnMap2.put("local_param", new ColumnScheme("local_param", "varchar(40)", false));
        columnMap2.putAll(columnMap);
        TableScheme table2 = new TableScheme(columnMap2, "simple_entity_impl2");
        assertEquals(table2, ((InheritanceMapper)mappingService.createMapping(SimpleEntityImpl2.class)).getTableScheme());

        assertEquals(table2, ((InheritanceMapper)mappingService.createMapping(m, SimpleEntityImpl2.class)).getTableScheme());
    }
}


