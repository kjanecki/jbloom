package com.agh.jbloom.components.mapping;

import com.agh.jbloom.annotations.Entity;
import com.agh.jbloom.annotations.Id;
import com.agh.jbloom.annotations.Table;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
@Entity
@Table(name = "simple_entity")
public class SimpleEntity{

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

