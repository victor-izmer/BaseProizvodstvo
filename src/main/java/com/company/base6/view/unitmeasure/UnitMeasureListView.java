package com.company.base6.view.unitmeasure;


import com.company.base6.entity.UnitMeasure;
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

@Route(value = "unitMeasures", layout = MainView.class)
@ViewController(id = "UnitMeasure.list")
@ViewDescriptor(path = "unit-measure-list-view.xml")
@LookupComponent("unitMeasuresDataGrid")
@DialogMode(width = "64em")
public class UnitMeasureListView extends StandardListView<UnitMeasure> {

    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<UnitMeasure> unitMeasuresDc;

    @ViewComponent
    private InstanceContainer<UnitMeasure> unitMeasureDc;

    @ViewComponent
    private InstanceLoader<UnitMeasure> unitMeasureDl;

    @ViewComponent
    private VerticalLayout listLayout;

    @ViewComponent
    private DataGrid<UnitMeasure> unitMeasuresDataGrid;

    @ViewComponent
    private FormLayout form;

    @ViewComponent
    private HorizontalLayout detailActions;

    @Subscribe
    public void onInit(final InitEvent event) {
        unitMeasuresDataGrid.getActions().forEach(action -> {
            if (action instanceof SecuredBaseAction secured) {
                secured.addEnabledRule(() -> listLayout.isEnabled());
            }
        });
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        updateControls(false);
    }

    @Subscribe("unitMeasuresDataGrid.create")
    public void onUnitMeasuresDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        UnitMeasure entity = dataContext.create(UnitMeasure.class);
        unitMeasureDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("unitMeasuresDataGrid.edit")
    public void onUnitMeasuresDataGridEdit(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveButton")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        UnitMeasure item = unitMeasureDc.getItem();
        ValidationErrors validationErrors = validateView(item);
        if (!validationErrors.isEmpty()) {
            ViewValidation viewValidation = getViewValidation();
            viewValidation.showValidationErrors(validationErrors);
            viewValidation.focusProblemComponent(validationErrors);
            return;
        }
        dataContext.save();
        unitMeasuresDc.replaceItem(item);
        updateControls(false);
    }

    @Subscribe("cancelButton")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        unitMeasureDc.setItem(null);
        unitMeasureDl.load();
        updateControls(false);
    }

    @Subscribe(id = "unitMeasuresDc", target = Target.DATA_CONTAINER)
    public void onUnitMeasuresDcItemChange(final InstanceContainer.ItemChangeEvent<UnitMeasure> event) {
        UnitMeasure entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            unitMeasureDl.setEntityId(entity.getId());
            unitMeasureDl.load();
        } else {
            unitMeasureDl.setEntityId(null);
            unitMeasureDc.setItem(null);
        }
        updateControls(false);
    }

    protected ValidationErrors validateView(UnitMeasure entity) {
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
        unitMeasuresDataGrid.getActions().forEach(Action::refreshState);
    }

    private ViewValidation getViewValidation() {
        return getApplicationContext().getBean(ViewValidation.class);
    }
}