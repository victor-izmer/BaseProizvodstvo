package com.company.base6.entity;

import io.jmix.core.MetadataTools;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

@JmixEntity
@Table(name = "BASKET", indexes = {
        @Index(name = "IDX_BASKET_REQUEST_REF", columnList = "REQUEST_REF_ID"),
        @Index(name = "IDX_BASKET_DETAL_REF", columnList = "DETAL_REF_ID"),
        @Index(name = "IDX_BASKET_UNITMEASURE_REF", columnList = "UNITMEASURE_REF_ID")
})
@Entity
public class Basket {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REQUEST_REF_ID")
    private Request requestRef;

    @JoinColumn(name = "DETAL_REF_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Workpiece detalRef;
    @JoinColumn(name = "OPERATION_REF_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Operation operationRef;
    @Column(name = "QUANTITY")
    private Float quantity;
    @JoinColumn(name = "UNITMEASURE_REF_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private UnitMeasure unitmeasureRef;
    @Column(name = "PRICE")
    private Float price;
    @Column(name = "NDS")
    private Boolean nds;
    @Column(name = "MATERIAL_OWNER")
    private Boolean materialOwner;
    @Column(name = "PRIM_BASKET", length = 1024)
    private String primBasket;

    public void setOperationRef(Operation operationRef) {
        this.operationRef = operationRef;
    }

    public Operation getOperationRef() {
        return operationRef;
    }

    public String getPrimBasket() {
        return primBasket;
    }

    public void setPrimBasket(String primBasket) {
        this.primBasket = primBasket;
    }

    public Boolean getMaterialOwner() {
        return materialOwner;
    }

    public void setMaterialOwner(Boolean materialOwner) {
        this.materialOwner = materialOwner;
    }

    public Boolean getNds() {
        return nds;
    }

    public void setNds(Boolean nds) {
        this.nds = nds;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public UnitMeasure getUnitmeasureRef() {
        return unitmeasureRef;
    }

    public void setUnitmeasureRef(UnitMeasure unitmeasureRef) {
        this.unitmeasureRef = unitmeasureRef;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Workpiece getDetalRef() {
        return detalRef;
    }

    public void setDetalRef(Workpiece detalRef) {
        this.detalRef = detalRef;
    }

    public Request getRequestRef() {
        return requestRef;
    }

    public void setRequestRef(Request requestRef) {
        this.requestRef = requestRef;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @InstanceName
    @DependsOnProperties({"requestRef", "detalRef", "operationRef"})
    public String getInstanceName(MetadataTools metadataTools) {
        return String.format("%s %s %s",
                metadataTools.format(requestRef),
                metadataTools.format(detalRef),
                metadataTools.format(operationRef));
    }
}