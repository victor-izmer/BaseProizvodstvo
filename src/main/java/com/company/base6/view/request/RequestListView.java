package com.company.base6.view.request;


import com.company.base6.entity.Request;
import com.company.base6.entity.User;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.core.DataManager;
import io.jmix.core.FileRef;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.processing.Filer;
import java.io.File;
import java.time.LocalDate;


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
    private UiComponents uiComponents;

    @Supply(to = "requestsDataGrid.file", subject = "renderer")
    private Renderer<Request> requestsDataGridFileRenderer() {
        return new ComponentRenderer<>(this::createFileComponent, this::fileComponentUpdater);
    }
    protected Span createFileComponent() {
        Span span = uiComponents.create(Span.class);
        span.getElement().getThemeList().add("contrast");
        span.setText("пусто");
        return span;
    }
    protected void fileComponentUpdater(Span span, Request request) {
        String fileName=  request.getScanInvoice();
        if (fileName != null) {
            File f = new File(fileName);
            String ss=f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf("Счета поставщиков\\")+17);
            span.setText(f.getName());
        } else {
            FileRef fr=  request.getFileInvoice();
            if (fr != null) {
                span.setText(fr.getFileName());
            }else  span.setText("Х");
        }

    }

}