package com.fidelity.backend.model.entity;

public abstract class BaseEntity<K extends Object> {

    protected K id;

    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }

}
