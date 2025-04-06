package com.company.base6.view.workpieceimages;

import com.company.base6.entity.WorkpieceImages;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.core.validation.group.UiCrossFieldChecks;
import io.jmix.flowui.action.SecuredBaseAction;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.kit.action.Action;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.model.InstanceLoader;
import io.jmix.flowui.view.*;

@Route(value = "workpiece-imageses", layout = MainView.class)
@ViewController(id = "WorkpieceImages.list")
@ViewDescriptor(path = "workpiece-images-list-view.xml")
@LookupComponent("workpieceImagesesDataGrid")
@DialogMode(width = "64em")
public class WorkpieceImagesListView extends StandardListView<WorkpieceImages> {

    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<WorkpieceImages> workpieceImagesesDc;

    @ViewComponent
    private InstanceContainer<WorkpieceImages> workpieceImagesDc;

    @ViewComponent
    private InstanceLoader<WorkpieceImages> workpieceImagesDl;

    @ViewComponent
    private VerticalLayout listLayout;

    @ViewComponent
    private DataGrid<WorkpieceImages> workpieceImagesesDataGrid;

    @ViewComponent
    private FormLayout form;

    @ViewComponent
    private HorizontalLayout detailActions;

    @Subscribe
    public void onInit(final InitEvent event) {
        workpieceImagesesDataGrid.getActions().forEach(action -> {
            if (action instanceof SecuredBaseAction secured) {
                secured.addEnabledRule(() -> listLayout.isEnabled());
            }
        });
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        updateControls(false);
    }

    @Subscribe("workpieceImagesesDataGrid.createAction")
    public void onWorkpieceImagesesDataGridCreateAction(final ActionPerformedEvent event) {
        dataContext.clear();
        WorkpieceImages entity = dataContext.create(WorkpieceImages.class);
        workpieceImagesDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("workpieceImagesesDataGrid.editAction")
    public void onWorkpieceImagesesDataGridEditAction(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveButton")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        WorkpieceImages item = workpieceImagesDc.getItem();
        ValidationErrors validationErrors = validateView(item);
        if (!validationErrors.isEmpty()) {
            ViewValidation viewValidation = getViewValidation();
            viewValidation.showValidationErrors(validationErrors);
            viewValidation.focusProblemComponent(validationErrors);
            return;
        }
        dataContext.save();
        workpieceImagesesDc.replaceItem(item);
        updateControls(false);
    }

    @Subscribe("cancelButton")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        workpieceImagesDc.setItem(null);
        workpieceImagesDl.load();
        updateControls(false);
    }

    @Subscribe(id = "workpieceImagesesDc", target = Target.DATA_CONTAINER)
    public void onWorkpieceImagesesDcItemChange(final InstanceContainer.ItemChangeEvent<WorkpieceImages> event) {
        WorkpieceImages entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            workpieceImagesDl.setEntityId(entity.getId());
            workpieceImagesDl.load();
        } else {
            workpieceImagesDl.setEntityId(null);
            workpieceImagesDc.setItem(null);
        }
        updateControls(false);
    }

    protected ValidationErrors validateView(WorkpieceImages entity) {
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
        workpieceImagesesDataGrid.getActions().forEach(Action::refreshState);
    }

    private ViewValidation getViewValidation() {
        return getApplicationContext().getBean(ViewValidation.class);
    }
}