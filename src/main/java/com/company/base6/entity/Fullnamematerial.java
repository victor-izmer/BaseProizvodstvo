package com.company.base6.entity;

import io.jmix.core.metamodel.annotation.Comment;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Set;

@JmixEntity
@Entity
@Table(name = "fullnamematerial")
public class Fullnamematerial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @InstanceName
    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material")
    private Material material;

    @Comment("Добавление характеристик сортамента")
    @OneToMany(mappedBy = "materialref")
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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Set<Workpiece> getWorkpieces() {
        return workpieces;
    }

    public void setWorkpieces(Set<Workpiece> workpieces) {
        this.workpieces = workpieces;
    }

}