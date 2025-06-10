package com.company.base6.view.request;

import com.company.base6.app.AdditionalFileStorageProcessor;
import com.company.base6.entity.Basket;
import com.company.base6.entity.Request;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.datepicker.TypedDatePicker;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.upload.FileStorageUploadField;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.kit.component.upload.event.FileUploadSucceededEvent;
import io.jmix.flowui.model.CollectionPropertyContainer;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

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

    @Autowired
    private AdditionalFileStorageProcessor additionalFileStorageProcessor;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Request> event) {
        Boolean t = true;
        System.out.println("Список заказа : Заказ: " + t);
    }

    @Autowired
    private Downloader downloader;
    @ViewComponent
    private IFrame pdfFrame;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        Request currentRequest = requestDc.getItem();

        if (currentRequest.getDateRequest() == null)
            getEditedEntity().setDateRequest(LocalDate.now());
        if (currentRequest.getDateControl() == null)
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

    @Subscribe("fieldFileInvoice")
    public void onFieldFileInvoiceFileUploadSucceeded(final FileUploadSucceededEvent<FileStorageUploadField> event) {
        additionalFileStorageProcessor.uploadFile("requestfs", event.getReceiver(), event.getSource(),
                () -> String.valueOf(getEditedEntity().getSupplierRef().getId()));
    }

//    @Subscribe(id = "ViewFile", subject = "clickListener")
//    public void onViewFileClick(final ClickEvent<JmixButton> event) throws URISyntaxException, IOException {
//        TypedTextField pdfPath = (TypedTextField) getContent().getComponent("scanInvoiceField");
//        String nameforpdf=(String) pdfPath.getTypedValue();
//        //openPDFInBrowser(nameforpdf);
//        getContent().getComponent("pdfFrame");
//    }
}