package com.company.base6.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

@JmixEntity
@Table(name = "SUPPLIER")
@Entity
public class Supplier {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @Id
    private Integer id;

    @InstanceName
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "WHAT_DO", length = 1024)
    private String whatDo;
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @JoinColumn(name = "TYPE_SUPPLIER_REF_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private TypeSupplier typeSupplierRef;
    @Column(name = "CONTACT", length = 1024)
    private String contact;
    @Column(name = "PRIM_SUPPLIER", length = 1024)
    private String primSupplier;
    @Column(name = "TEXT_REQUEST", length = 1024)
    private String textRequest;
    @Column(name = "PATH_FILES")
    private String pathFiles;

    public void setTypeSupplierRef(TypeSupplier typeSupplierRef) {
        this.typeSupplierRef = typeSupplierRef;
    }

    public TypeSupplier getTypeSupplierRef() {
        return typeSupplierRef;
    }

    public String getPathFiles() {
        return pathFiles;
    }

    public void setPathFiles(String pathFiles) {
        this.pathFiles = pathFiles;
    }

    public String getTextRequest() {
        return textRequest;
    }

    public void setTextRequest(String textRequest) {
        this.textRequest = textRequest;
    }

    public String getPrimSupplier() {
        return primSupplier;
    }

    public void setPrimSupplier(String primSupplier) {
        this.primSupplier = primSupplier;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getWhatDo() {
        return whatDo;
    }

    public void setWhatDo(String whatDo) {
        this.whatDo = whatDo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}