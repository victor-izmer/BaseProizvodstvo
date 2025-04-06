package com.company.base6.entity;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

@JmixEntity
@Entity
@Table(name = "specification", indexes = {
        @Index(name = "IDX_SPECIFICATION_DETALREF", columnList = "DETALREF_ID"),
        @Index(name = "IDX_SPECIFICATION_UNIT_MEASURE", columnList = "UNIT_MEASURE_ID")
})
public class Specification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "PARENTREF_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Specification parentref;
    @Column(name = "Количество")
    private Float количество;
    @JoinColumn(name = "UNIT_MEASURE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private UnitMeasure unitMeasure;
    @InstanceName
    @Lob
    @Column(name = "Примечание_спецификации")
    private String primSpec;


    @JoinColumn(name = "DETALREF_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Workpiece detalref;


    public UnitMeasure getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(UnitMeasure unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public Workpiece getDetalref() {
        return detalref;
    }

    public void setDetalref(Workpiece detalref) {
        this.detalref = detalref;
    }

    public void setParentref(Specification parentref) {
        this.parentref = parentref;
    }

    public Specification getParentref() {
        return parentref;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getКоличество() {
        return количество;
    }

    public void setКоличество(Float количество) {
        this.количество = количество;
    }

    public String getPrimSpec() {
        return primSpec;
    }

    public void setPrimSpec(String Field) {
        this.primSpec = Field;
    }

}