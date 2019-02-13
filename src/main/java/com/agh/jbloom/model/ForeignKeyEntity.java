package com.agh.jbloom.model;

import com.agh.jbloom.annotations.Entity;
import com.agh.jbloom.annotations.Id;
import com.agh.jbloom.annotations.OneToMany;
import com.agh.jbloom.annotations.Table;

import java.util.List;

@Entity
@Table(name = "foreign_key_entity")
public class ForeignKeyEntity {

    @Id
    private int id;
    private String name;

    @OneToMany(classname = "EntityExample")
    List<EntityExample> list;

    public ForeignKeyEntity() {
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

    public List<EntityExample> getList() {
        return list;
    }

    public void setList(List<EntityExample> list) {
        this.list = list;
    }
}
