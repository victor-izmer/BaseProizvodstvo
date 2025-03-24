package com.company.base6.entity;

import com.company.base6.Workpiece;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

@JmixEntity
@Table(name = "OPERATION")
@Entity
public class Operation {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private Long id;
    @InstanceName
    @JoinColumn(name = "TYPE_OPERATION_REF_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private TypeOperation typeOperationRef;
    @JoinColumn(name = "DETAIL_OUT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Workpiece detailOut;
    @Column(name = "PRICE_OP")
    private Float priceOp;
    @Column(name = "NDS")
    private Boolean nds;
    @Column(name = "PRIM_OP", length = 1024)
    private String primOp;
    @Column(name = "DURATION_OP")
    private Float durationOp;

    public void setDetailOut(Workpiece detailOut) {
        this.detailOut = detailOut;
    }

    public Workpiece getDetailOut() {
        return detailOut;
    }

    public void setTypeOperationRef(TypeOperation typeOperationRef) {
        this.typeOperationRef = typeOperationRef;
    }

    public TypeOperation getTypeOperationRef() {
        return typeOperationRef;
    }

    public Float getDurationOp() {
        return durationOp;
    }

    public void setDurationOp(Float durationOp) {
        this.durationOp = durationOp;
    }

    public String getPrimOp() {
        return primOp;
    }

    public void setPrimOp(String primOp) {
        this.primOp = primOp;
    }

    public Boolean getNds() {
        return nds;
    }

    public void setNds(Boolean nds) {
        this.nds = nds;
    }

    public Float getPriceOp() {
        return priceOp;
    }

    public void setPriceOp(Float priceOp) {
        this.priceOp = priceOp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}