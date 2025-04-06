package com.company.base6.entity;

import io.jmix.core.FileRef;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.UUID;

@JmixEntity
@Table(name = "WORKPIECE_IMAGES")
@Entity
public class WorkpieceImages {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;
    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;
    @InstanceName
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "FILE_PATH", length = 1024)
    private FileRef filePath;

    @JoinColumn(name = "WORKPIECE_REF_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Workpiece workpieceRef;
    @JoinColumn(name = "FILE_INFO_ID")
    @ManyToOne(fetch = FetchType.LAZY)


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Workpiece getWorkpieceRef() {
        return workpieceRef;
    }

    public void setWorkpieceRef(Workpiece workpieceRef) {
        this.workpieceRef = workpieceRef;
    }

    public FileRef getFilePath() {
        return filePath;
    }

    public void setFilePath(FileRef filePath) {
        this.filePath = filePath;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}