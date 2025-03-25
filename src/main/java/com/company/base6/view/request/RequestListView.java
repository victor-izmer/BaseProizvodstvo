package com.company.base6.view.request;

import com.company.base6.entity.Basket;
import com.company.base6.entity.Request;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.validation.group.UiCrossFieldChecks;
import io.jmix.flowui.action.SecuredBaseAction;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.grid.DataGrid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.kit.action.Action;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.model.InstanceLoader;
import io.jmix.flowui.view.*;


import io.jmix.flowui.Notifications;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.UI;

import static org.reflections.Reflections.log;

import com.company.base6.entity.Basket;
import com.company.base6.Workpiece;
import io.jmix.flowui.model.CollectionLoader;



@Route(value = "requests", layout = MainView.class)
@ViewController(id = "Request.list")
@ViewDescriptor(path = "request-list-view.xml")
@LookupComponent("requestsDataGrid")
@DialogMode(width = "64em")

public class RequestListView extends StandardListView<Request> {

    @Autowired
    private Notifications notifications;


    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<Request> requestsDc;

    @ViewComponent
    private InstanceContainer<Request> requestDc;

    @ViewComponent
    private InstanceLoader<Request> requestDl;

    @ViewComponent
    private VerticalLayout listLayout;

    @ViewComponent
    private DataGrid<Request> requestsDataGrid;

    @ViewComponent
    private FormLayout form;

    @ViewComponent
    private HorizontalLayout detailActions;

    @ViewComponent
    private CollectionLoader<Basket> basketsDl;

    @ViewComponent
    private InstanceLoader<Workpiece> workpieceDl;

    @ViewComponent
    private DataGrid<Basket> basketsDataGrid;


    @ViewComponent
    private InstanceContainer<Workpiece> workpieceDc;
    @ViewComponent
    private JmixImage<Object> drawImage;


    // Обработчик выбора заявки
    private void handleRequestSelection(SelectionEvent<Grid<Request>, Request> event) {
        event.getFirstSelectedItem().ifPresent(request -> {
            basketsDl.setParameter("requestId", request.getId());
            basketsDl.load();
        });
    }
    // Обработчик выбора корзины
    @Subscribe("basketsDataGrid")
    private void onDataGridSelect(SelectionEvent<Grid<Basket>, Basket> event) {
        event.getFirstSelectedItem().ifPresent(basket -> {
            if (basket.getDetalRef() != null) {
                workpieceDl.setParameter("workpieceId", basket.getDetalRef().getId());
                workpieceDl.load();
            }
        });
    }

    @Subscribe(id = "workpieceDc", target = Target.DATA_CONTAINER)
    public void onWorkpieceDcItemChange(InstanceContainer.ItemChangeEvent<Workpiece> event) {
        Workpiece workpiece = event.getItem();
        if (workpiece != null) {
            // Обновляем компоненты вручную
            updateMaterialField(workpiece);
            updateSizeField(workpiece);
            updatePhotoImage(workpiece);
            updateDrawImage(workpiece);
        }
    }
    private void updateMaterialField(Workpiece workpiece) {
        TextField materialField = (TextField) getContent().getComponent("materialField");
        if (materialField != null) {
            materialField.setValue(workpiece.getMaterialref() != null ? workpiece.getMaterialref().getDescription() : "");
        }
    }

    private void updateSizeField(Workpiece workpiece) {
        TextField sizeField = (TextField) getContent().getComponent("sizeField");
        if (sizeField != null) {
            sizeField.setValue(String.valueOf(workpiece.getРазмер()));
        }
    }

    private void updatePhotoImage(Workpiece workpiece) {
        Image photoImage = (Image) getContent().getComponent("photoImage");
        if (photoImage != null) {
            photoImage.setSrc(workpiece.getFullPhotoPath());
        }
    }
    private void updateDrawImage(Workpiece workpiece) {
        Image DrawImage = (Image) getContent().getComponent("drawImage");
        if (drawImage != null) {
            drawImage.setSrc(workpiece.getFullDrawPath());
        }
    }
    @Subscribe
    public void onInit(final InitEvent event) {
        requestsDataGrid.addSelectionListener(this::handleRequestSelection);
        basketsDataGrid.addSelectionListener(this::onDataGridSelect);

    }



    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        updateControls(false);
    }

    @Subscribe("requestsDataGrid.create")
    public void onRequestsDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        Request entity = dataContext.create(Request.class);
        requestDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("requestsDataGrid.edit")
    public void onRequestsDataGridEdit(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveButton")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        Request item = requestDc.getItem();
        ValidationErrors validationErrors = validateView(item);
        if (!validationErrors.isEmpty()) {
            ViewValidation viewValidation = getViewValidation();
            viewValidation.showValidationErrors(validationErrors);
            viewValidation.focusProblemComponent(validationErrors);
            return;
        }
        dataContext.save();
        requestsDc.replaceItem(item);
        updateControls(false);
    }

    @Subscribe("cancelButton")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        requestDc.setItem(null);
        requestDl.load();
        updateControls(false);
    }

    @Subscribe(id = "requestsDc", target = Target.DATA_CONTAINER)
    public void onRequestsDcItemChange(final InstanceContainer.ItemChangeEvent<Request> event) {
        Request entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            requestDl.setEntityId(entity.getId());
            requestDl.load();
        } else {
            requestDl.setEntityId(null);
            requestDc.setItem(null);
        }
        updateControls(false);
    }

    protected ValidationErrors validateView(Request entity) {
        ViewValidation viewValidation = getViewValidation();
        ValidationErrors validationErrors = viewValidation.validateUiComponents(form);
        if (!validationErrors.isEmpty()) {
            return validationErrors;
        }
        validationErrors.addAll(viewValidation.validateBeanGroup(UiCrossFieldChecks.class, entity));
        return validationErrors;
    }

    private void updateControls(boolean editing) {
        UiComponentUtils.getComponents(form).forEach(component -> {
            if (component instanceof HasValueAndElement<?, ?> field) {
                field.setReadOnly(!editing);
            }
        });

        detailActions.setVisible(editing);
        listLayout.setEnabled(!editing);
        requestsDataGrid.getActions().forEach(Action::refreshState);
    }

    private ViewValidation getViewValidation() {
        return getApplicationContext().getBean(ViewValidation.class);
    }

    @Subscribe("photoImage")
    public void onPhotoImageClick(ClickEvent<Image> event) {
        Workpiece workpiece = workpieceDc.getItem();
        if (workpiece != null) {
            String photoPath = workpiece.getFullPhotoPath();
            openImageInNewTab(photoPath);
        }
    }

    private void openImageInNewTab(String photoPath) {
        // Получаем текущий UI и Page
        UI.getCurrent().access(() -> {
            Page page = UI.getCurrent().getPage();
            String encodedPath = photoPath.replace(" ", "%20");
            page.executeJs("window.open($0, '_blank')", "file:///" + encodedPath);
        });
    }

}