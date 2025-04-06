package com.company.base6.entity;


import com.company.base6.DependsOnProperties;
import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JmixEntity
@Entity
@Table(name = "workpiece")
@Cacheable

public class Workpiece {
    @JmixGeneratedValue
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
    private String photoPath;

    @Column(name = "drawpath", length = 1024)
    private String drawPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parametrsizeref")
    private Parametrsize parametrsizeref;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "typeref", nullable = false)
    private Typeworkpiece typeref;


    /*private Path getPhotoBasePath() {
        //Path sdtr = Paths.get("${jmix.core.photoDir}");
        return Paths.get("photos/").toAbsolutePath();
    }*/
    @Transient
    @DependsOnProperties({"наименование", "fullPhotopath"})
    public String getFullPhotoPath() {
        String fileName =  "/photos/Комплектующие/" + наименование + "_" + номерЧертежа + "/" + photoPath;
        fileName= fileName.replace(" ", "_"); // Заменяем " " на "_"
        return fileName;
    }


    public String getFullDrawPath() {

        String fileName = "/photos/Комплектующие/" + наименование + "_" + номерЧертежа + "/" + drawPath;
        fileName= fileName.replace(" ", "_"); // Заменяем " " на "_"
        return fileName;
    }


    public void setDrawPath(String drawPath) {
        this.drawPath = drawPath;
    }

    public String getDrawPath() {
        return drawPath;
    }

    public void setPhotoPath(String photoPath) {

            this.photoPath = photoPath;

    }

    public String getPhotoPath() {
        return photoPath;
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