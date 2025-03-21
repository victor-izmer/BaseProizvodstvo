package com.company.base6.view.request;

import com.company.base6.entity.Request;
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

@Route(value = "requests", layout = MainView.class)
@ViewController(id = "Request.list")
@ViewDescriptor(path = "request-list-view.xml")
@LookupComponent("requestsDataGrid")
@DialogMode(width = "64em")
public class RequestListView extends StandardListView<Request> {

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

    @Subscribe
    public void onInit(final InitEvent event) {
        requestsDataGrid.getActions().forEach(action -> {
            if (action instanceof SecuredBaseAction secured) {
                secured.addEnabledRule(() -> listLayout.isEnabled());
            }
        });
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
}