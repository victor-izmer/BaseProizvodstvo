package com.company.base6.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum TypeOperationTable implements EnumClass<Integer> {

    ПОКРАСКА(1),
    ЛАЗЕРНАЯ_РЕЗКА(2),
    ГРАВИРОВКА(3),
    МЕХОБРАБОТКА(4),
    ДАВИЛЬНЫЕ_РАБОТЫ(5),
    ВНУТР_РАБОТЫ(6),
    СТЕКЛОДУВЫ(7),
    СВАРКА(8),
    ЗАКУПКА(9),
    ГАЛЬВАНИКА(18);

    private final Integer id;

    TypeOperationTable(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static TypeOperationTable fromId(Integer id) {
        for (TypeOperationTable at : TypeOperationTable.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}