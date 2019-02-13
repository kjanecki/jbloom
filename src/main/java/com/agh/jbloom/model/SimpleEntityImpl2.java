package com.agh.jbloom.model;

import com.agh.jbloom.annotations.Entity;
import com.agh.jbloom.annotations.Table;

@Entity
@Table(name = "my_simple_entity_impl2")
public class SimpleEntityImpl2 extends SimpleEntityImpl{

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

    @Override
    public String toString() {
        return "SimpleEntityImpl2{" +
                "local_param='" + local_param + '\'' +
                ", param=" + param +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}



