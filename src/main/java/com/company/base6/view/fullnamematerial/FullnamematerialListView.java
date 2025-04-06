package com.company.base6.view.fullnamematerial;

import com.company.base6.entity.Fullnamematerial;
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

@Route(value = "fullnamematerials", layout = MainView.class)
@ViewController(id = "Fullnamematerial.list")
@ViewDescriptor(path = "fullnamematerial-list-view.xml")
@LookupComponent("fullnamematerialsDataGrid")
@DialogMode(width = "64em")
public class FullnamematerialListView extends StandardListView<Fullnamematerial> {

    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<Fullnamematerial> fullnamematerialsDc;

    @ViewComponent
    private InstanceContainer<Fullnamematerial> fullnamematerialDc;

    @ViewComponent
    private InstanceLoader<Fullnamematerial> fullnamematerialDl;

    @ViewComponent
    private VerticalLayout listLayout;

    @ViewComponent
    private DataGrid<Fullnamematerial> fullnamematerialsDataGrid;

    @ViewComponent
    private FormLayout form;

    @ViewComponent
    private HorizontalLayout detailActions;

    @Subscribe
    public void onInit(final InitEvent event) {
        fullnamematerialsDataGrid.getActions().forEach(action -> {
            if (action instanceof SecuredBaseAction secured) {
                secured.addEnabledRule(() -> listLayout.isEnabled());
            }
        });
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        updateControls(false);
    }

    @Subscribe("fullnamematerialsDataGrid.create")
    public void onFullnamematerialsDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        Fullnamematerial entity = dataContext.create(Fullnamematerial.class);
        fullnamematerialDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("fullnamematerialsDataGrid.edit")
    public void onFullnamematerialsDataGridEdit(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveButton")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        Fullnamematerial item = fullnamematerialDc.getItem();
        ValidationErrors validationErrors = validateView(item);
        if (!validationErrors.isEmpty()) {
            ViewValidation viewValidation = getViewValidation();
            viewValidation.showValidationErrors(validationErrors);
            viewValidation.focusProblemComponent(validationErrors);
            return;
        }
        dataContext.save();
        fullnamematerialsDc.replaceItem(item);
        updateControls(false);
    }

    @Subscribe("cancelButton")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        fullnamematerialDc.setItem(null);
        fullnamematerialDl.load();
        updateControls(false);
    }

    @Subscribe(id = "fullnamematerialsDc", target = Target.DATA_CONTAINER)
    public void onFullnamematerialsDcItemChange(final InstanceContainer.ItemChangeEvent<Fullnamematerial> event) {
        Fullnamematerial entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            fullnamematerialDl.setEntityId(entity.getId());
            fullnamematerialDl.load();
        } else {
            fullnamematerialDl.setEntityId(null);
            fullnamematerialDc.setItem(null);
        }
        updateControls(false);
    }

    protected ValidationErrors validateView(Fullnamematerial entity) {
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
        fullnamematerialsDataGrid.getActions().forEach(Action::refreshState);
    }

    private ViewValidation getViewValidation() {
        return getApplicationContext().getBean(ViewValidation.class);
    }
}