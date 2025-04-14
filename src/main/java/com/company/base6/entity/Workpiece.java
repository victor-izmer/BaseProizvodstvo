package com.company.base6.entity;


import com.company.base6.DependsOnProperties;
import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.slf4j.LoggerFactory;

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
    private String description;

    @Size(max = 255)
    @Column(name = "Номер_чертежа")
    private String numDraw;

    @Size(max = 255)
    @Column(name = "Примечание_детали")
    private String primDetal;

    @Column(name = "Цена_последняя")
    private Float priceLast;

    @Column(name = "Цена_последняя_НДС")
    private Boolean priceLastNDS;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materialref")
    private Fullnamematerial materialref;

    @Column(name = "РАЗМЕР")
    private Float size;

    @Column(name = "Время_изготовления")
    private Short timeProduce;

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
    @JmixProperty
    @DependsOnProperties({"наименование", "photopath"})
    public String getFullPhotoPath() {
        String fileName =  "photos/Комплектующие/" + description + "_" + numDraw + "/" + photoPath;
        //String fileName =  "/photos/Комплектующие/" + id + "/" + "Фото_"+ id+ ".jpg";
        fileName= fileName.replace(" ", "_"); // Заменяем " " на "_"
        return fileName;
    }
    @JmixProperty
    @DependsOnProperties({"наименование", "drawpath"})
    public String getFullDrawPath() {
        String fileName = "photos/Комплектующие/" + description + "_" + numDraw + "/" + drawPath;
        fileName= fileName.replace(" ", "_"); // Заменяем " " на "_"
        return fileName;
    }
    @JmixProperty
    @DependsOnProperties({"наименование", "photopath"})
    public String getPhotoId() {
        String fileName =  "photos/Комплектующие/" + id + "/" + "Фото_"+id+".jpg";

        return fileName;
    }
    @JmixProperty
    @DependsOnProperties({"наименование", "drawpath"})
    public String getDrawId() {
        String fileName =  "photos/Комплектующие/" + id + "/" + "Чертеж_"+id+".jpg";

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
        LoggerFactory.getLogger(Workpiece.class).info("Фото: {}", photoPath);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumDraw() {
        return numDraw;
    }

    public void setNumDraw(String numDraw) {
        this.numDraw = numDraw;
    }

    public String getPrimDetal() {
        return primDetal;
    }

    public void setPrimDetal(String primDetal) {
        this.primDetal = primDetal;
    }

    public Float getPriceLast() {
        return priceLast;
    }

    public void setPriceLast(Float priceLast) {
        this.priceLast = priceLast;
    }

    public Boolean getPriceLastNDS() {
        return priceLastNDS;
    }

    public void setPriceLastNDS(Boolean priceLastNDS) {
        this.priceLastNDS = priceLastNDS;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public Short getTimeProduce() {
        return timeProduce;
    }

    public void setTimeProduce(Short timeProduce) {
        this.timeProduce = timeProduce;
    }


}