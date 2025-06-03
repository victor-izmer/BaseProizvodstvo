package com.company.base6.view.workpiece;

import com.company.base6.entity.NewsItem;
import com.company.base6.entity.Request;
import com.company.base6.entity.Workpiece;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoUtility;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import io.jmix.flowui.UiComponents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

@Route(value = "workpieces/:id", layout = MainView.class)
@ViewController(id = "Workpiece.detail")
@ViewDescriptor(path = "workpiece-detail-view.xml")
@EditedEntityContainer("workpieceDc")

public class WorkpieceDetailView extends StandardDetailView<Workpiece> {
    protected static final String SRC_PATH = "icons/jmix-icon.png";
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private FileStorage fileStorage;
    @Autowired
    private Notifications notifications;



//    @Subscribe
//    public void onReady(final ReadyEvent event) {
//        initImage2();
//    }
//
//    protected void initImage2() {
//        Image drawImage = (Image) getContent().getComponent("drawImage");
//        String fileName = getEditedEntity().getDrawId();
//        drawImage.setSrc(fileName);
//        Image photoImage = (Image) getContent().getComponent("photoImage");
//        fileName = getEditedEntity().getPhotoId();
//        photoImage.setSrc(fileName);
//
//    }


}