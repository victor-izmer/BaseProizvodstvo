package com.company.base6.view.typeworkpiece;


import com.company.base6.entity.Typeworkpiece;
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

@Route(value = "typeworkpieces", layout = MainView.class)
@ViewController(id = "Typeworkpiece.list")
@ViewDescriptor(path = "typeworkpiece-list-view.xml")
@LookupComponent("typeworkpiecesDataGrid")
@DialogMode(width = "64em")
public class TypeworkpieceListView extends StandardListView<Typeworkpiece> {

    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<Typeworkpiece> typeworkpiecesDc;

    @ViewComponent
    private InstanceContainer<Typeworkpiece> typeworkpieceDc;

    @ViewComponent
    private InstanceLoader<Typeworkpiece> typeworkpieceDl;

    @ViewComponent
    private VerticalLayout listLayout;

    @ViewComponent
    private DataGrid<Typeworkpiece> typeworkpiecesDataGrid;

    @ViewComponent
    private FormLayout form;

    @ViewComponent
    private HorizontalLayout detailActions;

    @Subscribe
    public void onInit(final InitEvent event) {
        typeworkpiecesDataGrid.getActions().forEach(action -> {
            if (action instanceof SecuredBaseAction secured) {
                secured.addEnabledRule(() -> listLayout.isEnabled());
            }
        });
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        updateControls(false);
    }

    @Subscribe("typeworkpiecesDataGrid.create")
    public void onTypeworkpiecesDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        Typeworkpiece entity = dataContext.create(Typeworkpiece.class);
        typeworkpieceDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("typeworkpiecesDataGrid.edit")
    public void onTypeworkpiecesDataGridEdit(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveButton")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        Typeworkpiece item = typeworkpieceDc.getItem();
        ValidationErrors validationErrors = validateView(item);
        if (!validationErrors.isEmpty()) {
            ViewValidation viewValidation = getViewValidation();
            viewValidation.showValidationErrors(validationErrors);
            viewValidation.focusProblemComponent(validationErrors);
            return;
        }
        dataContext.save();
        typeworkpiecesDc.replaceItem(item);
        updateControls(false);
    }

    @Subscribe("cancelButton")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        typeworkpieceDc.setItem(null);
        typeworkpieceDl.load();
        updateControls(false);
    }

    @Subscribe(id = "typeworkpiecesDc", target = Target.DATA_CONTAINER)
    public void onTypeworkpiecesDcItemChange(final InstanceContainer.ItemChangeEvent<Typeworkpiece> event) {
        Typeworkpiece entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            typeworkpieceDl.setEntityId(entity.getId());
            typeworkpieceDl.load();
        } else {
            typeworkpieceDl.setEntityId(null);
            typeworkpieceDc.setItem(null);
        }
        updateControls(false);
    }

    protected ValidationErrors validateView(Typeworkpiece entity) {
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
        typeworkpiecesDataGrid.getActions().forEach(Action::refreshState);
    }

    private ViewValidation getViewValidation() {
        return getApplicationContext().getBean(ViewValidation.class);
    }
}