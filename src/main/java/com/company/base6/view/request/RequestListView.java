package com.company.base6.view.request;

import com.company.base6.app.RequestService;
import com.company.base6.entity.Request;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


@Route(value = "requests", layout = MainView.class)
@ViewController(id = "Request.list")
@ViewDescriptor(path = "request-list-view.xml")
@LookupComponent("requestsDataGrid")
@DialogMode(width = "64em")
public class RequestListView extends StandardListView<Request> {
    @ViewComponent
    private DataGrid<Request> requestsDataGrid;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Notifications notifications;
    @Autowired
    private RequestService requestService;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        Boolean t=true;
        System.out.println("Тест список запросов: " +t);
    }

    @Subscribe
    public void onInit(final InitEvent event) {
        Objects.requireNonNull(requestsDataGrid.getColumnByKey("scan")).addClassName("scan-column");
    }

    @Subscribe(id = "CountSum", subject = "clickListener")
    public void onCountSumClick(final ClickEvent<JmixButton> event) {
        final Long sumCount = dataManager.loadValue("select COUNT(b.price) from Basket b", Long.class).one();
        notifications.show("Сумма "+ sumCount);
    }

    @Subscribe(id = "button", subject = "clickListener")
    public void onButtonClick(final ClickEvent<JmixButton> event) {
        Request request=requestsDataGrid.getSingleSelectedItem();
        requestService.notifyOverDue(request);
    }
}