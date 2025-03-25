package com.company.base6;

import io.jmix.core.DataManager;
import io.jmix.core.DeletePolicy;
import io.jmix.core.FileRef;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

@JmixEntity
@Entity
@Table(name = "workpiece")
public class Workpiece {


    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @InstanceName
    @Size(max = 255)
    @Column(name = "Наименование")
    private String наименование;

    @Size(max = 255)
    @Column(name = "Номер_чертежа")
    private String номерЧертежа;

    @Size(max = 255)
    @Column(name = "Примечание_детали")
    private String примечаниеДетали;

    @Column(name = "Цена_последняя")
    private Float ценаПоследняя;

    @Column(name = "Цена_последняя_НДС")
    private Boolean ценаПоследняяНдс;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materialref")
    private Fullnamematerial materialref;

    @Column(name = "РАЗМЕР")
    private Float размер;

    @Column(name = "Время_изготовления")
    private Short времяИзготовления;

    @Column(name = "photopath", length = 1024)
    private String photopath;

    @Column(name = "drawpath", length = 1024)
    private String drawpath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parametrsizeref")
    private Parametrsize parametrsizeref;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "typeref", nullable = false)
    private Typeworkpiece typeref;

    @JmixProperty
    @DependsOnProperties({"наименование", "photopath"})
    public String getFullPhotoPath() {
        String fileName = "photos/Комплектующие/" + наименование + "_" + номерЧертежа + "/" + photopath;
        fileName= fileName.replace(" ", "_"); // Заменяем " " на "_"
        return fileName;
    }
    @JmixProperty
    @DependsOnProperties({"наименование", "drawpath"})
    public String getFullDrawPath() {
        String fileName = "photos/Комплектующие/" + наименование + "_" + номерЧертежа + "/" + drawpath;
        fileName= fileName.replace(" ", "_"); // Заменяем " " на "_"
        return fileName;
    }


    public void setDrawpath(String drawpath) {
        this.drawpath = drawpath;
    }

    public String getDrawpath() {
        return drawpath;
    }

    public void setPhotopath(String photopath) {

            this.photopath = photopath;

    }

    public String getPhotopath() {
        return photopath;
    }

    public void setMaterialref(Fullnamematerial materialref) {
        this.materialref = materialref;
    }

    public Fullnamematerial getMaterialref() {
        return materialref;
    }

    public Typeworkpiece getTyperef() {
        return typeref;
    }

    public void setTyperef(Typeworkpiece typeref) {
        this.typeref = typeref;
    }

    public Parametrsize getParametrsizeref() {
        return parametrsizeref;
    }

    public void setParametrsizeref(Parametrsize parametrsizeref) {
        this.parametrsizeref = parametrsizeref;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getНаименование() {
        return наименование;
    }

    public void setНаименование(String наименование) {
        this.наименование = наименование;
    }

    public String getНомерЧертежа() {
        return номерЧертежа;
    }

    public void setНомерЧертежа(String номерЧертежа) {
        this.номерЧертежа = номерЧертежа;
    }

    public String getПримечаниеДетали() {
        return примечаниеДетали;
    }

    public void setПримечаниеДетали(String примечаниеДетали) {
        this.примечаниеДетали = примечаниеДетали;
    }

    public Float getЦенаПоследняя() {
        return ценаПоследняя;
    }

    public void setЦенаПоследняя(Float ценаПоследняя) {
        this.ценаПоследняя = ценаПоследняя;
    }

    public Boolean getЦенаПоследняяНдс() {
        return ценаПоследняяНдс;
    }

    public void setЦенаПоследняяНдс(Boolean ценаПоследняяНдс) {
        this.ценаПоследняяНдс = ценаПоследняяНдс;
    }

    public Float getРазмер() {
        return размер;
    }

    public void setРазмер(Float размер) {
        this.размер = размер;
    }

    public Short getВремяИзготовления() {
        return времяИзготовления;
    }

    public void setВремяИзготовления(Short времяИзготовления) {
        this.времяИзготовления = времяИзготовления;
    }


}