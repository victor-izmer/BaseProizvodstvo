package com.company.base6;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;
import io.jmix.core.metamodel.annotation.JmixEntity;

@JmixEntity
@Entity
@Table(name = "fullnamematerial")
public class Fullnamematerial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material")
    private Material material;

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