package com.company.base6.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@JmixEntity
@Table(name = "STATUS_ZAYAVKA")
@Entity
public class StatusZayavka {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private Long id;
    @InstanceName
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "COLOR")
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}