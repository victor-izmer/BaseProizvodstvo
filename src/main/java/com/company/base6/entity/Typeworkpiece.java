package com.company.base6.entity;

import io.jmix.core.FileRef;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Set;

@JmixEntity
@Entity
@Table(name = "typeworkpiece")
public class Typeworkpiece {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @InstanceName
    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @Column(name = "Картинка", length = 1024)
    private FileRef picture;

    @OneToMany(mappedBy = "typeref")
    private Set<Workpiece> workpieces;

    public void setPicture(FileRef picture) {
        this.picture = picture;
    }

    public FileRef getPicture() {
        return picture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Workpiece> getWorkpieces() {
        return workpieces;
    }

    public void setWorkpieces(Set<Workpiece> workpieces) {
        this.workpieces = workpieces;
    }


}