package com.agh.jbloom.components.mapping;

import com.agh.jbloom.components.query.BaseSqlTypeConverter;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

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
    public void canCreateMappingForSingleClass(){
        TableScheme table = new TableScheme(base, "simple_entity");
        BaseMapperHandler handler = mappingService.createMapping(SimpleEntity.class);
        assertEquals(table,handler.getMapper().getTableScheme());
    }

    @Test
    public void canCreateMappingForDerivedClasses(){
        Map<String, ColumnScheme> columnMap = new HashMap<>();
        columnMap.put("param", new ColumnScheme("param", "numeric(10,5)", false));
        columnMap.putAll(base);
        TableScheme table1 = new TableScheme(columnMap, "simple_entity_impl");
        BaseMapperHandler m = mappingService.createMapping(SimpleEntityImpl.class);
        assertEquals(table1, m.getMapper().getTableScheme());

        Map<String, ColumnScheme> columnMap2 = new HashMap<>();
        columnMap2.put("local_param", new ColumnScheme("local_param", "varchar(40)", false));
        columnMap2.putAll(columnMap);
        TableScheme table2 = new TableScheme(columnMap2, "simple_entity_impl2");
        assertEquals(table2, mappingService.createMapping(SimpleEntityImpl2.class).getMapper().getTableScheme());

        assertEquals(table2, mappingService.createMapping(m, SimpleEntityImpl2.class).getMapper().getTableScheme());
    }
}


