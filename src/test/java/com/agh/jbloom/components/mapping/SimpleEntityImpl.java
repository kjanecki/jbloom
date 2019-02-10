package com.agh.jbloom.components.mapping;

import com.agh.jbloom.annotations.Entity;
import com.agh.jbloom.annotations.Table;
import org.springframework.boot.test.context.TestComponent;

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

