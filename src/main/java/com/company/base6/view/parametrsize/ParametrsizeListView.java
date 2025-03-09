package com.company.base6.view.parametrsize;

import com.company.base6.Parametrsize;
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

@Route(value = "parametrsizes", layout = MainView.class)
@ViewController(id = "Parametrsize.list")
@ViewDescriptor(path = "parametrsize-list-view.xml")
@LookupComponent("parametrsizesDataGrid")
@DialogMode(width = "64em")
public class ParametrsizeListView extends StandardListView<Parametrsize> {

    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<Parametrsize> parametrsizesDc;

    @ViewComponent
    private InstanceContainer<Parametrsize> parametrsizeDc;

    @ViewComponent
    private InstanceLoader<Parametrsize> parametrsizeDl;

    @ViewComponent
    private VerticalLayout listLayout;

    @ViewComponent
    private DataGrid<Parametrsize> parametrsizesDataGrid;

    @ViewComponent
    private FormLayout form;

    @ViewComponent
    private HorizontalLayout detailActions;

    @Subscribe
    public void onInit(final InitEvent event) {
        parametrsizesDataGrid.getActions().forEach(action -> {
            if (action instanceof SecuredBaseAction secured) {
                secured.addEnabledRule(() -> listLayout.isEnabled());
            }
        });
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        updateControls(false);
    }

    @Subscribe("parametrsizesDataGrid.create")
    public void onParametrsizesDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        Parametrsize entity = dataContext.create(Parametrsize.class);
        parametrsizeDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("parametrsizesDataGrid.edit")
    public void onParametrsizesDataGridEdit(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveButton")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        Parametrsize item = parametrsizeDc.getItem();
        ValidationErrors validationErrors = validateView(item);
        if (!validationErrors.isEmpty()) {
            ViewValidation viewValidation = getViewValidation();
            viewValidation.showValidationErrors(validationErrors);
            viewValidation.focusProblemComponent(validationErrors);
            return;
        }
        dataContext.save();
        parametrsizesDc.replaceItem(item);
        updateControls(false);
    }

    @Subscribe("cancelButton")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        parametrsizeDc.setItem(null);
        parametrsizeDl.load();
        updateControls(false);
    }

    @Subscribe(id = "parametrsizesDc", target = Target.DATA_CONTAINER)
    public void onParametrsizesDcItemChange(final InstanceContainer.ItemChangeEvent<Parametrsize> event) {
        Parametrsize entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            parametrsizeDl.setEntityId(entity.getId());
            parametrsizeDl.load();
        } else {
            parametrsizeDl.setEntityId(null);
            parametrsizeDc.setItem(null);
        }
        updateControls(false);
    }

    protected ValidationErrors validateView(Parametrsize entity) {
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
        parametrsizesDataGrid.getActions().forEach(Action::refreshState);
    }

    private ViewValidation getViewValidation() {
        return getApplicationContext().getBean(ViewValidation.class);
    }
}