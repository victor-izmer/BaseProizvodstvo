package com.company.base6.entity;


import io.jmix.core.DeletePolicy;
import io.jmix.core.FileRef;
import io.jmix.core.MetadataTools;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.*;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.Subscribe;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.slf4j.LoggerFactory;

import java.util.List;

@JmixEntity
@Entity
@Table(name = "workpiece", indexes = {
        @Index(name = "IDX_WORKPIECE_BASKET", columnList = "BASKET_ID")
})
@Cacheable

public class Workpiece {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

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

    @Column(name = "PHOTO_FILE_REF", length = 1024)
    private FileRef photoFileRef;

    @Column(name = "drawpath", length = 1024)
    private String drawPath;

    @Column(name = "DRAW_PATH_REF", length = 1024)
    private FileRef drawPathRef;

    @Column(name = "DRAW_FILE_REF", length = 1024)
    private FileRef drawFileRef;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parametrsizeref")
    private Parametrsize parametrsizeref;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "typeref", nullable = false)
    private Typeworkpiece typeref;

    @Composition
    @OneToMany(mappedBy = "detailOut")
    private List<Operation> operationRef;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @JoinColumn(name = "BASKET_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Basket basket;


    public FileRef getDrawFileRef() {
        return drawFileRef;
    }

    public void setDrawFileRef(FileRef drawFileRef) {
        this.drawFileRef = drawFileRef;
    }

    public FileRef getPhotoFileRef() {
        return photoFileRef;
    }

    public void setPhotoFileRef(FileRef photoFileRef) {
        this.photoFileRef = photoFileRef;
    }

    public FileRef getDrawPathRef() {
        return drawPathRef;
    }

    public void setDrawPathRef(FileRef drawPathRef) {
        this.drawPathRef = drawPathRef;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public List<Operation> getOperationRef() {
        return operationRef;
    }

    public void setOperationRef(List<Operation> operationRef) {
        this.operationRef = operationRef;
    }


    /*private Path getPhotoBasePath() {
        //Path sdtr = Paths.get("${jmix.core.photoDir}");
        return Paths.get("photos/").toAbsolutePath();
    }*/
    @JmixProperty
    public String getFullPhotoPath() {
        String fileName =  "photos/Комплектующие/" + description + "_" + numDraw + "/" + photoPath;
        //String fileName =  "/photos/Комплектующие/" + id + "/" + "Фото_"+ id+ ".jpg";
        fileName= fileName.replace(" ", "_"); // Заменяем " " на "_"
        return fileName;
    }
    @JmixProperty
    public String getFullDrawPath() {
        String fileName = "photos/Комплектующие/" + description + "_" + numDraw + "/" + drawPath;
        fileName= fileName.replace(" ", "_"); // Заменяем " " на "_"
        return fileName;
    }
    @JmixProperty
    public String getPhotoId() {
        String fileName =  "photos/Комплектующие/" + id + "/" + "Фото_"+id+".jpg";

        return fileName;
    }
    @JmixProperty
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

    @JmixProperty
    @DependsOnProperties({"description", "numDraw"})
    public String getFullDescription() {
        if  (this.numDraw!= null) return this.description + " " + this.numDraw;
        else return this.description;
    }
    @JmixProperty
    @DependsOnProperties({"priceLast", "priceLastNDS"})
    public String getPriceNDS() {
        if(this.priceLastNDS!= null & this.priceLast!= null) {
            if(this.priceLast!=0) {
                if (this.priceLastNDS) return this.priceLast / 1.2 + "(" + this.priceLast + ")";
                else return this.priceLast + "(" + this.priceLast * 1.2 + ")";
            }
        }
        return "";
    }

    @InstanceName
    @DependsOnProperties({"description", "numDraw"})
    public String getInstanceName(MetadataTools metadataTools) {
        return String.format("%s %s",
                metadataTools.format(description),
                metadataTools.format(numDraw));
    }
}