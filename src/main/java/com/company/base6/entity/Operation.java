package com.company.base6.entity;


import io.jmix.core.DeletePolicy;
import io.jmix.core.MetadataTools;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

@JmixEntity
@Table(name = "OPERATION")
@Entity
public class Operation {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Id
    private Long id;
    @Column(name = "TYPE_OPERATION_REF")
    private Integer typeOperationRef;
    @OnDeleteInverse(DeletePolicy.CASCADE)
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

    public void setTypeOperationRef(TypeOperationTable typeOperationRef) {
        this.typeOperationRef = typeOperationRef == null ? null : typeOperationRef.getId();
    }

    public TypeOperationTable getTypeOperationRef() {
        return typeOperationRef == null ? null : TypeOperationTable.fromId(typeOperationRef);
    }

    public void setDetailOut(Workpiece detailOut) {
        this.detailOut = detailOut;
    }

    public Workpiece getDetailOut() {
        return detailOut;
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

    @InstanceName
    @DependsOnProperties({"typeOperationRef", "durationOp"})
    public String getInstanceName(MetadataTools metadataTools) {
        return String.format("%s : %sмин",
                metadataTools.format(getTypeOperationRef()),
                metadataTools.format(durationOp));
    }
}