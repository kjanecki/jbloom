package com.agh.jbloom.model;

import com.agh.jbloom.annotations.Entity;
import com.agh.jbloom.annotations.Table;

@Entity
@Table(name = "simple_entity_impl")
public class SimpleEntityImpl extends  SimpleEntity {

    protected Double param;

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

