package com.company.base6.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.FileRef;
import io.jmix.core.MetadataTools;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.datatype.DatatypeFormatter;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@JmixEntity
@Table(name = "REQUEST", indexes = {
        @Index(name = "IDX_REQUEST_SUPPLIER_REF", columnList = "SUPPLIER_REF_ID"),
        @Index(name = "IDX_REQUEST_STATUS_ZAYAVKA_REF", columnList = "STATUS_ZAYAVKA_REF_ID")
})
@Entity
@Cacheable
public class Request {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Id
    private Long id;

    @Column(name = "DATE_REQUEST")
    private LocalDate dateRequest;

    @Column(name = "DATE_CONTROL")
    private LocalDate dateControl;

    @Column(name = "DATE_FINISH")
    private LocalDate dateFinish;

    @Column(name = "SCAN_INVOICE")
    private String scanInvoice;

    @Column(name = "FILE_INVOICE", length = 1024)
    private FileRef fileInvoice;

    @Column(name = "PRIM_REQUEST", length = 1024)
    private String primRequest;

    @Column(name = "SUM_REQUEST")
    private Float sumRequest;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @JoinColumn(name = "STATUS_ZAYAVKA_REF_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private StatusZayavka statusZayavkaRef;

    @Composition
    @OneToMany(mappedBy = "requestRef")
    private List<Basket> basketRef;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @JoinColumn(name = "SUPPLIER_REF_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Supplier supplierRef;

    public FileRef getFileInvoice() {
        return fileInvoice;
    }

    public void setFileInvoice(FileRef fileInvoice) {
        this.fileInvoice = fileInvoice;
    }

    public void setDateFinish(LocalDate dateFinish) {
        this.dateFinish = dateFinish;
    }

    public LocalDate getDateFinish() {
        return dateFinish;
    }

    public void setDateControl(LocalDate dateControl) {
        this.dateControl = dateControl;
    }

    public LocalDate getDateControl() {
        return dateControl;
    }

    public List<Basket> getBasketRef() {
        return basketRef;
    }

    public void setBasketRef(List<Basket> basketRef) {
        this.basketRef = basketRef;
    }

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

    public String getScanInvoice() {
        return scanInvoice;
    }

    public void setScanInvoice(String scanInvoice) {
        this.scanInvoice = scanInvoice;
    }

    public LocalDate getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(LocalDate dateRequest) {
        this.dateRequest = dateRequest == null ? LocalDate.now() : dateRequest;
        //this.dateRequest = dateRequest;
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
                datatypeFormatter.formatLocalDate(dateRequest));
    }
}