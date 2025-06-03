package com.company.base6.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.MetadataTools;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.List;

@JmixEntity
@Table(name = "BASKET", indexes = {
        @Index(name = "IDX_BASKET_REQUEST_REF", columnList = "REQUEST_REF_ID"),
        @Index(name = "IDX_BASKET_UNITMEASURE_REF", columnList = "UNITMEASURE_REF_ID"),
        @Index(name = "IDX_BASKET_OPERATION_REF", columnList = "OPERATION_REF_ID")
})
@Entity
public class Basket {

    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "QUANTITY")
    private Float quantity;

    @Column(name = "PRICE")
    private Float price;

    @Column(name = "NDS")
    private Boolean nds;

    @Column(name = "MATERIAL_OWNER")
    private Boolean materialOwner;

    @Column(name = "PRIM_BASKET", length = 1024)
    private String primBasket;

    @JoinColumn(name = "UNITMEASURE_REF_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private UnitMeasure unitmeasureRef;

    @JoinColumn(name = "OPERATION_REF_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Operation operationRef;

    @Composition
    @OneToMany(mappedBy = "basket")
    private List<Workpiece> detalComp;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REQUEST_REF_ID")
    private Request requestRef;

    public List<Workpiece> getDetalComp() {
        return detalComp;
    }

    public void setDetalComp(List<Workpiece> detalComp) {
        this.detalComp = detalComp;
    }

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
    @DependsOnProperties({"requestRef", "operationRef"})
    public String getInstanceName(MetadataTools metadataTools) {
        return String.format("%s %s",
                metadataTools.format(requestRef),
                metadataTools.format(operationRef));
    }
}