package com.company.base6.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum StatusExecute implements EnumClass<Integer> {

    CREATE(10),
    IN_WORK(20),
    WORK_OUT(30);

    private final Integer id;

    StatusExecute(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static StatusExecute fromId(Integer id) {
        for (StatusExecute at : StatusExecute.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}