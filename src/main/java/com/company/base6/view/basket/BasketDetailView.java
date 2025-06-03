package com.company.base6.view.basket;

import com.company.base6.entity.*;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;

import java.awt.*;
import java.time.LocalDate;

@Route(value = "baskets/:id", layout = MainView.class)
@ViewController(id = "Basket.detail")
@ViewDescriptor(path = "basket-detail-view.xml")
@EditedEntityContainer("basketDc")
public class BasketDetailView extends StandardDetailView<Basket> {
    @ViewComponent
    private CollectionContainer<Workpiece> workpiecesDc;
    @ViewComponent
    private InstanceContainer<Basket> basketDc;
    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {

    }

    @ViewComponent
    private CollectionLoader<Operation> operationsDl;
    @ViewComponent
    private EntityComboBox<Workpiece> basketsComboBox;

    @ViewComponent
    private TypedTextField<Float> priceFieldText;

//    @ViewComponent
//    private TypedTextField<Object> priceFieldText;

    @Subscribe("basketsComboBox")
    public void onBasketsComboBoxComponentValueChange(final AbstractField.ComponentValueChangeEvent<EntityComboBox<Workpiece>, Workpiece> event) {
        Workpiece selectedDetail = basketsComboBox.getValue();

        if(getEditedEntity().getPrice()== null)getEditedEntity().setPrice(selectedDetail.getPriceLast());
        if(getEditedEntity().getQuantity()== null){getEditedEntity().setQuantity(1F);}

        operationsDl.setParameter("detail", selectedDetail); // предполагается, что selectedDetail связан с detailOut
        operationsDl.load();
    }


    @Subscribe
    public void onReady(final ReadyEvent event) {
        Basket basket = basketDc.getItem();
        if (basket == null) {
            return;
        }

        Workpiece selectedDetail= new Workpiece();
        Operation operation = basket.getOperationRef();
        if (operation !=null) {
            selectedDetail=operation.getDetailOut();
            basketsComboBox.setValue(selectedDetail);
        };

    }




}