package com.company.base6;

import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@JmixEntity
@Entity
@Table(name = "specification", indexes = {
        @Index(name = "IDX_SPECIFICATION_UNITREF", columnList = "id")
})
public class Specification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "detailref", nullable = false)
    private Workpiece detailref;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parentref", nullable = false)
    private Workpiece parentref;

    @Column(name = "Количество")
    private Float количество;

    @JoinColumn(name = "unitref", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UnitMeasure unitref;

    @Lob
    @Column(name = "Примечание_спецификации")
    private String примечаниеСпецификации;

    public UnitMeasure getUnitref() {
        return unitref;
    }

    public void setUnitref(UnitMeasure unitref) {
        this.unitref = unitref;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Workpiece getDetailref() {
        return detailref;
    }

    public void setDetailref(Workpiece detailref) {
        this.detailref = detailref;
    }

    public Workpiece getParentref() {
        return parentref;
    }

    public void setParentref(Workpiece parentref) {
        this.parentref = parentref;
    }

    public Float getКоличество() {
        return количество;
    }

    public void setКоличество(Float количество) {
        this.количество = количество;
    }

    public String getПримечаниеСпецификации() {
        return примечаниеСпецификации;
    }

    public void setПримечаниеСпецификации(String Field) {
        this.примечаниеСпецификации = Field;
    }

}