package com.agh.jbloom.components.mapping;

import com.agh.jbloom.annotations.Entity;
import com.agh.jbloom.annotations.Table;
import org.springframework.boot.test.context.TestComponent;

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



