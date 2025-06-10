package com.company.base6.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;

@JmixEntity
@Entity
@Cacheable
@Table(name = "specification", indexes = {
        @Index(name = "IDX_SPECIFICATION_DETALREF", columnList = "DETALREF_ID"),
        @Index(name = "IDX_SPECIFICATION_UNIT_MEASURE", columnList = "UNIT_MEASURE_ID")
})
public class Specification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "PARENTREF_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Specification parentref;

    @PositiveOrZero(message = "Только положительное количество")
    @Column(name = "Количество")
    private Float skolko;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @JoinColumn(name = "UNIT_MEASURE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private UnitMeasure unitMeasure;

    @InstanceName
    @Lob
    @Column(name = "Примечание_спецификации")
    private String primSpec;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "DETALREF_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Workpiece detalref;

    @Column(name = "PRICE_DETAL")
    private Float priceDetalOperation;

    @Column(name = "PRICE_DETAL_ASSEMBLE")
    private Float priceDetalAssemble;

    @JmixProperty
    @DependsOnProperties({"priceDetalAssemble", "priceDetalOperation"})
    public Float getTotalPrice() {
        return Objects.requireNonNullElse(priceDetalOperation, 0f) +
                Objects.requireNonNullElse(priceDetalAssemble, 0f);
    }


    public Float getPriceDetalAssemble() {
        return priceDetalAssemble;
    }

    public void setPriceDetalAssemble(Float priceDetalAssemble) {
        this.priceDetalAssemble = priceDetalAssemble;
    }


    public Float getPriceDetalOperation() {
        return priceDetalOperation;
    }

    public void setPriceDetalOperation(Float priceDetalOperation) {
        this.priceDetalOperation = priceDetalOperation;
    }


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

    public Float getSkolko() {
        return skolko;
    }

    public void setSkolko(Float skolko) {
        this.skolko = skolko;
    }

    public String getPrimSpec() {
        return primSpec;
    }

    public void setPrimSpec(String Field) {
        this.primSpec = Field;
    }

    @JmixProperty
    @DependsOnProperties({"skolko", "unitMeasure"})
    public String getFullSkolko() {
        if(this.skolko !=null & this.unitMeasure!=null){
            if(this.skolko!=0) {
                return this.skolko + " " + this.unitMeasure.getDescription();
            }
        }
        return "";
    }
}