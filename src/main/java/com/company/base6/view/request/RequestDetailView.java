package com.company.base6.view.request;

import com.company.base6.entity.Request;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.Boolean.TRUE;

@Route(value = "requests/:id", layout = MainView.class)
@ViewController(id = "Request.detail")
@ViewDescriptor(path = "request-detail-view.xml")
@EditedEntityContainer("requestDc")
public class RequestDetailView extends StandardDetailView<Request> {
    @Autowired
    private DataManager dataManager;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Request> event) {
        Boolean t=true;
        System.out.println("Список заявок : Заявка: " +t);
    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        // final Long sumCount = dataManager.loadValue("select COUNT(b.price) from Basket b", Long.class).one();
        //notifications.show("Сумма "+ sumCount);
    }
    @Autowired
    private Notifications notifications;
    private void openPDFInBrowser(String pdfPath) throws URISyntaxException, IOException {
        Path absolutePath = Paths.get(pdfPath).toAbsolutePath();

        UI.getCurrent().access(() -> {
            try {
                // 3. Открываем в браузере
                Page page = UI.getCurrent().getPage();
                page.executeJs("window.open($0)", pdfPath, "popup");
            } catch (Exception e) {
                notifications.show("Ошибка открытия изображения");
            }
        });
    }
    @Subscribe(id = "ViewFile", subject = "clickListener")
    public void onViewFileClick(final ClickEvent<JmixButton> event) throws URISyntaxException, IOException {
        TypedTextField pdfPath = (TypedTextField) getContent().getComponent("scanInvoiceField");
        String nameforpdf=(String) pdfPath.getTypedValue();
        //openPDFInBrowser(nameforpdf);
        getContent().getComponent("pdfFrame");
    }
}