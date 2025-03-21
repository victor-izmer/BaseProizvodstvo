package com.company.base6.view.basket;

import com.company.base6.entity.Basket;
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

@Route(value = "baskets", layout = MainView.class)
@ViewController(id = "Basket.list")
@ViewDescriptor(path = "basket-list-view.xml")
@LookupComponent("basketsDataGrid")
@DialogMode(width = "64em")
public class BasketListView extends StandardListView<Basket> {

    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<Basket> basketsDc;

    @ViewComponent
    private InstanceContainer<Basket> basketDc;

    @ViewComponent
    private InstanceLoader<Basket> basketDl;

    @ViewComponent
    private VerticalLayout listLayout;

    @ViewComponent
    private DataGrid<Basket> basketsDataGrid;

    @ViewComponent
    private FormLayout form;

    @ViewComponent
    private HorizontalLayout detailActions;

    @Subscribe
    public void onInit(final InitEvent event) {
        basketsDataGrid.getActions().forEach(action -> {
            if (action instanceof SecuredBaseAction secured) {
                secured.addEnabledRule(() -> listLayout.isEnabled());
            }
        });
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        updateControls(false);
    }

    @Subscribe("basketsDataGrid.create")
    public void onBasketsDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        Basket entity = dataContext.create(Basket.class);
        basketDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("basketsDataGrid.edit")
    public void onBasketsDataGridEdit(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveButton")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        Basket item = basketDc.getItem();
        ValidationErrors validationErrors = validateView(item);
        if (!validationErrors.isEmpty()) {
            ViewValidation viewValidation = getViewValidation();
            viewValidation.showValidationErrors(validationErrors);
            viewValidation.focusProblemComponent(validationErrors);
            return;
        }
        dataContext.save();
        basketsDc.replaceItem(item);
        updateControls(false);
    }

    @Subscribe("cancelButton")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        basketDc.setItem(null);
        basketDl.load();
        updateControls(false);
    }

    @Subscribe(id = "basketsDc", target = Target.DATA_CONTAINER)
    public void onBasketsDcItemChange(final InstanceContainer.ItemChangeEvent<Basket> event) {
        Basket entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            basketDl.setEntityId(entity.getId());
            basketDl.load();
        } else {
            basketDl.setEntityId(null);
            basketDc.setItem(null);
        }
        updateControls(false);
    }

    protected ValidationErrors validateView(Basket entity) {
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
        basketsDataGrid.getActions().forEach(Action::refreshState);
    }

    private ViewValidation getViewValidation() {
        return getApplicationContext().getBean(ViewValidation.class);
    }
}