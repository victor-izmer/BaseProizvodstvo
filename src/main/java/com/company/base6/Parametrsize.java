package com.company.base6;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@JmixEntity
@Entity
@Table(name = "parametrsize")
public class Parametrsize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @InstanceName
    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "parametrsizeref")
    private Set<Workpiece> workpieces;

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