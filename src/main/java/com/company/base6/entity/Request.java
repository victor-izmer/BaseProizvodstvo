package com.company.base6.entity;

import io.jmix.core.MetadataTools;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.datatype.DatatypeFormatter;
import jakarta.persistence.*;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.InstanceLoader;
import io.jmix.flowui.component.grid.DataGrid;

import java.util.Date;

@JmixEntity
@Table(name = "REQUEST", indexes = {
        @Index(name = "IDX_REQUEST_SUPPLIER_REF", columnList = "SUPPLIER_REF_ID"),
        @Index(name = "IDX_REQUEST_STATUS_ZAYAVKA_REF", columnList = "STATUS_ZAYAVKA_REF_ID")
})
@Entity
public class Request {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private Long id;
    @JoinColumn(name = "SUPPLIER_REF_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Supplier supplierRef;
    @Column(name = "NUM_INVOICE")
    private String numInvoice;
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_REQUEST")
    private Date dateRequest;
    @Column(name = "SCAN_INVOICE")
    private String scanInvoice;
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_CONTROL")
    private Date dateControl;
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_FINISH")
    private Date dateFinish;
    @Column(name = "PRIM_REQUEST", length = 1024)
    private String primRequest;
    @JoinColumn(name = "STATUS_ZAYAVKA_REF_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private StatusZayavka statusZayavkaRef;
    @Column(name = "SUM_REQUEST")
    private Float sumRequest;

    public Float getSumRequest() {
        return sumRequest;
    }

    public void setSumRequest(Float sumRequest) {
        this.sumRequest = sumRequest;
    }

    public StatusZayavka getStatusZayavkaRef() {
        return statusZayavkaRef;
    }

    public void setStatusZayavkaRef(StatusZayavka statusZayavkaRef) {
        this.statusZayavkaRef = statusZayavkaRef;
    }

    public String getPrimRequest() {
        return primRequest;
    }

    public void setPrimRequest(String primRequest) {
        this.primRequest = primRequest;
    }

    public Date getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }

    public Date getDateControl() {
        return dateControl;
    }

    public void setDateControl(Date dateControl) {
        this.dateControl = dateControl;
    }

    public String getScanInvoice() {
        return scanInvoice;
    }

    public void setScanInvoice(String scanInvoice) {
        this.scanInvoice = scanInvoice;
    }

    public Date getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(Date dateRequest) {
        this.dateRequest = dateRequest;
    }

    public String getNumInvoice() {
        return numInvoice;
    }

    public void setNumInvoice(String numInvoice) {
        this.numInvoice = numInvoice;
    }

    public Supplier getSupplierRef() {
        return supplierRef;
    }

    public void setSupplierRef(Supplier supplierRef) {
        this.supplierRef = supplierRef;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @InstanceName
    @DependsOnProperties({"supplierRef", "dateRequest"})
    public String getInstanceName(MetadataTools metadataTools, DatatypeFormatter datatypeFormatter) {
        return String.format("%s %s",
                metadataTools.format(supplierRef),
                datatypeFormatter.formatDate(dateRequest));
    }
}