package com.company.base6.view.workpiece;


import com.company.base6.entity.Workpiece;
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

@Route(value = "workpieces", layout = MainView.class)
@ViewController(id = "Workpiece.list")
@ViewDescriptor(path = "workpiece-list-view.xml")
@LookupComponent("workpiecesDataGrid")
@DialogMode(width = "64em")
public class WorkpieceListView extends StandardListView<Workpiece> {

    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<Workpiece> workpiecesDc;

    @ViewComponent
    private InstanceContainer<Workpiece> workpieceDc;

    @ViewComponent
    private InstanceLoader<Workpiece> workpieceDl;

    @ViewComponent
    private VerticalLayout listLayout;

    @ViewComponent
    private DataGrid<Workpiece> workpiecesDataGrid;

    @ViewComponent
    private FormLayout form;

    @ViewComponent
    private HorizontalLayout detailActions;

    @Subscribe
    public void onInit(final InitEvent event) {
        workpiecesDataGrid.getActions().forEach(action -> {
            if (action instanceof SecuredBaseAction secured) {
                secured.addEnabledRule(() -> listLayout.isEnabled());
            }
        });
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        updateControls(false);
    }

    @Subscribe("workpiecesDataGrid.create")
    public void onWorkpiecesDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        Workpiece entity = dataContext.create(Workpiece.class);
        workpieceDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("workpiecesDataGrid.edit")
    public void onWorkpiecesDataGridEdit(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveButton")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        Workpiece item = workpieceDc.getItem();
        ValidationErrors validationErrors = validateView(item);
        if (!validationErrors.isEmpty()) {
            ViewValidation viewValidation = getViewValidation();
            viewValidation.showValidationErrors(validationErrors);
            viewValidation.focusProblemComponent(validationErrors);
            return;
        }
        dataContext.save();
        workpiecesDc.replaceItem(item);
        updateControls(false);
    }

    @Subscribe("cancelButton")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        workpieceDc.setItem(null);
        workpieceDl.load();
        updateControls(false);
    }

    @Subscribe(id = "workpiecesDc", target = Target.DATA_CONTAINER)
    public void onWorkpiecesDcItemChange(final InstanceContainer.ItemChangeEvent<Workpiece> event) {
        Workpiece entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            workpieceDl.setEntityId(entity.getId());
            workpieceDl.load();
        } else {
            workpieceDl.setEntityId(null);
            workpieceDc.setItem(null);
        }
        updateControls(false);
    }

    protected ValidationErrors validateView(Workpiece entity) {
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
        workpiecesDataGrid.getActions().forEach(Action::refreshState);
    }

    private ViewValidation getViewValidation() {
        return getApplicationContext().getBean(ViewValidation.class);
    }
}