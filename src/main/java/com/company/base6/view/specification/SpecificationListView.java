package com.company.base6.view.specification;

import com.company.base6.Specification;
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

@Route(value = "specifications", layout = MainView.class)
@ViewController(id = "Specification.list")
@ViewDescriptor(path = "specification-list-view.xml")
@LookupComponent("specificationsDataGrid")
@DialogMode(width = "64em")
public class SpecificationListView extends StandardListView<Specification> {

    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<Specification> specificationsDc;

    @ViewComponent
    private InstanceContainer<Specification> specificationDc;

    @ViewComponent
    private InstanceLoader<Specification> specificationDl;

    @ViewComponent
    private VerticalLayout listLayout;

    @ViewComponent
    private DataGrid<Specification> specificationsDataGrid;

    @ViewComponent
    private FormLayout form;

    @ViewComponent
    private HorizontalLayout detailActions;

    @Subscribe
    public void onInit(final InitEvent event) {
        specificationsDataGrid.getActions().forEach(action -> {
            if (action instanceof SecuredBaseAction secured) {
                secured.addEnabledRule(() -> listLayout.isEnabled());
            }
        });
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        updateControls(false);
    }

    @Subscribe("specificationsDataGrid.create")
    public void onSpecificationsDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        Specification entity = dataContext.create(Specification.class);
        specificationDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("specificationsDataGrid.edit")
    public void onSpecificationsDataGridEdit(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveButton")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        Specification item = specificationDc.getItem();
        ValidationErrors validationErrors = validateView(item);
        if (!validationErrors.isEmpty()) {
            ViewValidation viewValidation = getViewValidation();
            viewValidation.showValidationErrors(validationErrors);
            viewValidation.focusProblemComponent(validationErrors);
            return;
        }
        dataContext.save();
        specificationsDc.replaceItem(item);
        updateControls(false);
    }

    @Subscribe("cancelButton")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        specificationDc.setItem(null);
        specificationDl.load();
        updateControls(false);
    }

    @Subscribe(id = "specificationsDc", target = Target.DATA_CONTAINER)
    public void onSpecificationsDcItemChange(final InstanceContainer.ItemChangeEvent<Specification> event) {
        Specification entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            specificationDl.setEntityId(entity.getId());
            specificationDl.load();
        } else {
            specificationDl.setEntityId(null);
            specificationDc.setItem(null);
        }
        updateControls(false);
    }

    protected ValidationErrors validateView(Specification entity) {
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
        specificationsDataGrid.getActions().forEach(Action::refreshState);
    }

    private ViewValidation getViewValidation() {
        return getApplicationContext().getBean(ViewValidation.class);
    }
}