package com.company.base6.view.request;

import com.company.base6.entity.Basket;
import com.company.base6.entity.Request;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import io.jmix.core.*;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.datepicker.TypedDatePicker;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.upload.FileStorageUploadField;
import io.jmix.flowui.download.DownloadFormat;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionPropertyContainer;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static java.lang.Boolean.TRUE;

@Route(value = "requests/:id", layout = MainView.class)
@ViewController(id = "Request.detail")
@ViewDescriptor(path = "request-detail-view.xml")
@EditedEntityContainer("requestDc")
public class RequestDetailView extends StandardDetailView<Request> {
    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private CollectionPropertyContainer<Basket> basketRefDc;
    @ViewComponent
    private DataGrid<Basket> basketRefDataGrid;
    @ViewComponent
    private InstanceContainer<Request> requestDc;
    @ViewComponent
    private FileStorageUploadField fieldFileInvoice;
    @ViewComponent
    private TypedDatePicker<Comparable> dateControlField;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Request> event) {
        Boolean t=true;
        System.out.println("Список заказа : Заказ: " +t);
    }
    @Autowired
    private Downloader downloader;
    @ViewComponent
    private IFrame pdfFrame;
    @Autowired
    private FileStorage fileStorage;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        Request currentRequest=requestDc.getItem();

        if(currentRequest.getDateRequest()== null)
            getEditedEntity().setDateRequest(LocalDate.now());
        if(currentRequest.getDateControl()== null)
            getEditedEntity().setDateControl(LocalDate.now().plusDays(5));

    }


    @Autowired
    private UiComponents uiComponents;

    @Supply(to = "basketRefDataGrid.nds", subject = "renderer")
    private Renderer<Basket> basketRefDataGridNdsRenderer() {
        return new ComponentRenderer<>(
                () -> {
                    JmixCheckbox checkbox = uiComponents.create(JmixCheckbox.class);
                    checkbox.setReadOnly(true);
                    return checkbox;
                },
                (checkbox, basket) -> checkbox.setValue(basket.getNds())
        );
    }
//    @Subscribe(id = "ViewFile", subject = "clickListener")
//    public void onViewFileClick(final ClickEvent<JmixButton> event) throws URISyntaxException, IOException {
//        TypedTextField pdfPath = (TypedTextField) getContent().getComponent("scanInvoiceField");
//        String nameforpdf=(String) pdfPath.getTypedValue();
//        //openPDFInBrowser(nameforpdf);
//        getContent().getComponent("pdfFrame");
//    }
}