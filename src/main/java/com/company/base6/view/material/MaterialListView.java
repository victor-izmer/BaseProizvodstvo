package com.company.base6.view.material;

import com.company.base6.entity.Material;
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

@Route(value = "materials", layout = MainView.class)
@ViewController(id = "Material.list")
@ViewDescriptor(path = "material-list-view.xml")
@LookupComponent("materialsDataGrid")
@DialogMode(width = "64em")
public class MaterialListView extends StandardListView<Material> {

    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<Material> materialsDc;

    @ViewComponent
    private InstanceContainer<Material> materialDc;

    @ViewComponent
    private InstanceLoader<Material> materialDl;

    @ViewComponent
    private VerticalLayout listLayout;

    @ViewComponent
    private DataGrid<Material> materialsDataGrid;

    @ViewComponent
    private FormLayout form;

    @ViewComponent
    private HorizontalLayout detailActions;

    @Subscribe
    public void onInit(final InitEvent event) {
        materialsDataGrid.getActions().forEach(action -> {
            if (action instanceof SecuredBaseAction secured) {
                secured.addEnabledRule(() -> listLayout.isEnabled());
            }
        });
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        updateControls(false);
    }

    @Subscribe("materialsDataGrid.create")
    public void onMaterialsDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        Material entity = dataContext.create(Material.class);
        materialDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("materialsDataGrid.edit")
    public void onMaterialsDataGridEdit(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveButton")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        Material item = materialDc.getItem();
        ValidationErrors validationErrors = validateView(item);
        if (!validationErrors.isEmpty()) {
            ViewValidation viewValidation = getViewValidation();
            viewValidation.showValidationErrors(validationErrors);
            viewValidation.focusProblemComponent(validationErrors);
            return;
        }
        dataContext.save();
        materialsDc.replaceItem(item);
        updateControls(false);
    }

    @Subscribe("cancelButton")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        materialDc.setItem(null);
        materialDl.load();
        updateControls(false);
    }

    @Subscribe(id = "materialsDc", target = Target.DATA_CONTAINER)
    public void onMaterialsDcItemChange(final InstanceContainer.ItemChangeEvent<Material> event) {
        Material entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            materialDl.setEntityId(entity.getId());
            materialDl.load();
        } else {
            materialDl.setEntityId(null);
            materialDc.setItem(null);
        }
        updateControls(false);
    }

    protected ValidationErrors validateView(Material entity) {
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
        materialsDataGrid.getActions().forEach(Action::refreshState);
    }

    private ViewValidation getViewValidation() {
        return getApplicationContext().getBean(ViewValidation.class);
    }
}