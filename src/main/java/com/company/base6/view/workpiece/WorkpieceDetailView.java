package com.company.base6.view.workpiece;

import com.company.base6.app.AdditionalFileStorageProcessor;
import com.company.base6.entity.Workpiece;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.component.upload.FileStorageUploadField;
import io.jmix.flowui.kit.component.upload.event.FileUploadSucceededEvent;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "workpieces/:id", layout = MainView.class)
@ViewController(id = "Workpiece.detail")
@ViewDescriptor(path = "workpiece-detail-view.xml")
@EditedEntityContainer("workpieceDc")

public class WorkpieceDetailView extends StandardDetailView<Workpiece> {
    protected static final String SRC_PATH = "icons/jmix-icon.png";
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Notifications notifications;
    @Autowired
    private AdditionalFileStorageProcessor additionalFileStorageProcessor;

    @Subscribe("PhotoStorageUpload")
    public void onPhotoStorageUploadFileUploadSucceeded(final FileUploadSucceededEvent<FileStorageUploadField> event) {
        additionalFileStorageProcessor.uploadFile("workpiecefs", event.getReceiver(), event.getSource(),
                () -> String.valueOf(getEditedEntity().getId()));
    }

    @Subscribe("DrawStorageUpload")
    public void onDrawStorageUploadFileUploadSucceeded(final FileUploadSucceededEvent<FileStorageUploadField> event) {
        additionalFileStorageProcessor.uploadFile("workpiecefs", event.getReceiver(), event.getSource(),
                () -> String.valueOf(getEditedEntity().getId()));
    }

    @Subscribe(id = "photo", subject = "clickListener")
    public void onPhotoClick(final ClickEvent<JmixImage<?>> event) {
        UI.getCurrent().getPage().open(event.getSource().getSrc());
    }

    @Subscribe(id = "graph", subject = "clickListener")
    public void onGraphClick(final ClickEvent<JmixImage<?>> event) {
        UI.getCurrent().getPage().open(event.getSource().getSrc());
    }


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