package com.agh.jbloom.model;

import com.agh.jbloom.annotations.Entity;
import com.agh.jbloom.annotations.Id;
import com.agh.jbloom.annotations.Table;

@Entity
@Table(name = "simple_entity")
public class SimpleEntity{

    @Id
    protected int id;
    protected String name;
    protected String description;

    protected String dupa;

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

    public String getDupa() {
        return dupa;
    }

    public void setDupa(String dupa) {
        this.dupa = dupa;
    }
}

