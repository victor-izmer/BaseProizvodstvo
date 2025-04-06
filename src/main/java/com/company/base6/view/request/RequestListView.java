package com.company.base6.view.request;


import com.company.base6.entity.Basket;
import com.company.base6.entity.Request;
import com.company.base6.entity.Workpiece;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.FileStorageLocator;
import io.jmix.core.validation.group.UiCrossFieldChecks;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.kit.action.Action;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.*;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;


@Route(value = "requests", layout = MainView.class)
@ViewController(id = "Request.list")
@ViewDescriptor(path = "request-list-view.xml")
@LookupComponent("requestsDataGrid")
@DialogMode(width = "64em")

public class RequestListView extends StandardListView<Request> {

    @Autowired
    private Notifications notifications;


    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<Request> requestsDc;

    @ViewComponent
    private InstanceContainer<Request> requestDc;

    @ViewComponent
    private InstanceLoader<Request> requestDl;

    @ViewComponent
    private VerticalLayout listLayout;

    @ViewComponent
    private DataGrid<Request> requestsDataGrid;

    @ViewComponent
    private FormLayout form;

    @ViewComponent
    private HorizontalLayout detailActions;

    @ViewComponent
    private CollectionLoader<Basket> basketsDl;

    @ViewComponent
    private InstanceLoader<Workpiece> workpieceDl;

    @ViewComponent
    private DataGrid<Basket> basketsDataGrid;


    @ViewComponent
    private InstanceContainer<Workpiece> workpieceDc;
    @ViewComponent
    private JmixImage<Object> drawImage;
    @ViewComponent
    private JmixImage<Object> photoImage;



    // Обработчик выбора заявки
    private void handleRequestSelection(SelectionEvent<Grid<Request>, Request> event) {
        event.getFirstSelectedItem().ifPresent(request -> {
            basketsDl.setParameter("requestId", request.getId());
            basketsDl.load();
        });
    }
    // Обработчик выбора корзины
    @Subscribe("basketsDataGrid")
    private void onDataGridSelect(SelectionEvent<Grid<Basket>, Basket> event) {
        event.getFirstSelectedItem().ifPresent(basket -> {
            if (basket.getDetalRef() != null) {
                workpieceDl.setParameter("workpieceId", basket.getDetalRef().getId());
                workpieceDl.load();
            }
        });
    }

    @Subscribe(id = "workpieceDc", target = Target.DATA_CONTAINER)
    public void onWorkpieceDcItemChange(InstanceContainer.ItemChangeEvent<Workpiece> event) {
        Workpiece workpiece = event.getItem();
        if (workpiece != null) {
            // Обновляем компоненты вручную
            updateMaterialField(workpiece);
            updateSizeField(workpiece);
            updatePhotoImage(workpiece);
            updateDrawImage(workpiece);
        }
    }
    private void updateMaterialField(Workpiece workpiece) {
        TextField materialField = (TextField) getContent().getComponent("materialField");
        if (materialField != null) {
            materialField.setValue(workpiece.getMaterialref() != null ? workpiece.getMaterialref().getDescription() : "");
        }
    }

    private void updateSizeField(Workpiece workpiece) {
        TextField sizeField = (TextField) getContent().getComponent("sizeField");
        if (sizeField != null) {
            sizeField.setValue(String.valueOf(workpiece.getРазмер()));
        }
    }

    private void updatePhotoImage(Workpiece workpiece) {
        Image photoImage = (Image) getContent().getComponent("photoImage");
        if (photoImage != null) {
            photoImage.setSrc(workpiece.getFullPhotoPath());
        }
    }
    private void updateDrawImage(Workpiece workpiece) {
        Image DrawImage = (Image) getContent().getComponent("drawImage");
        if (drawImage != null) {
            drawImage.setSrc(workpiece.getFullDrawPath());
        }
    }
    @Subscribe
    public void onInit(final InitEvent event) {
        requestsDataGrid.addSelectionListener(this::handleRequestSelection);
        basketsDataGrid.addSelectionListener(this::onDataGridSelect);

    }



    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        updateControls(false);
    }

    @Subscribe("requestsDataGrid.create")
    public void onRequestsDataGridCreate(final ActionPerformedEvent event) {
        dataContext.clear();
        Request entity = dataContext.create(Request.class);
        requestDc.setItem(entity);
        updateControls(true);
    }

    @Subscribe("requestsDataGrid.edit")
    public void onRequestsDataGridEdit(final ActionPerformedEvent event) {
        updateControls(true);
    }

    @Subscribe("saveButton")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        Request item = requestDc.getItem();
        ValidationErrors validationErrors = validateView(item);
        if (!validationErrors.isEmpty()) {
            ViewValidation viewValidation = getViewValidation();
            viewValidation.showValidationErrors(validationErrors);
            viewValidation.focusProblemComponent(validationErrors);
            return;
        }
        dataContext.save();
        requestsDc.replaceItem(item);
        updateControls(false);
    }

    @Subscribe("cancelButton")
    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.clear();
        requestDc.setItem(null);
        requestDl.load();
        updateControls(false);
    }

    @Subscribe(id = "requestsDc", target = Target.DATA_CONTAINER)
    public void onRequestsDcItemChange(final InstanceContainer.ItemChangeEvent<Request> event) {
        Request entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            requestDl.setEntityId(entity.getId());
            requestDl.load();
        } else {
            requestDl.setEntityId(null);
            requestDc.setItem(null);
        }
        updateControls(false);
    }

    protected ValidationErrors validateView(Request entity) {
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
        requestsDataGrid.getActions().forEach(Action::refreshState);
    }

    private ViewValidation getViewValidation() {
        return getApplicationContext().getBean(ViewValidation.class);
    }

    @Subscribe("photoImage")
    public void onPhotoImageClick(ClickEvent<Image> event) throws IOException, InterruptedException, URISyntaxException {
        Workpiece workpiece = workpieceDc.getItem();
        if (workpiece != null) {
            String photoPath = workpiece.getFullPhotoPath();
            openImageInBrowser(photoPath);
        }

    }
    @Autowired
    private Downloader downloader;

    @Value("${jmix.core.photoDir}")
    private String photoDir;

    private Path getPhotoBasePath() {
        //Path sdtr = Paths.get("${jmix.core.photoDir}");
        return Paths.get(photoDir).toAbsolutePath();
    }
    @Autowired
    private FileStorageLocator fileStorageLocator;

    private void openImageInBrowser(String photoPath) throws URISyntaxException, IOException {

        //saveToLocalFile(photoPath);
        Path absolutePath = Paths.get(photoPath).toAbsolutePath();
        //String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        // Формируем полный URL к статическому ресурсу
        //String fullUrl = baseUrl + "/" + photoPath;

        // Создаем FileRef из строки

        //Runtime.getRuntime().exec("cmd.exe /c Start " + absolutePath.toString());
        // System.out.println(absolutePath);
        //FileRef fileRef =FileRef.fromString(photoPath);
        String strfile = absolutePath.toString();
        //Path filenamestr = absolutePath.getFileName();

        //String fnsStr = filenamestr.toString();
        //Path fnsFull = filenamestr.getRoot();
        //strfile=fnsStr;
        //ResourceHandlerRegistry registry;
        //uploadFile(photoPath);
        saveToLocalFile(photoPath);
        //saveToLocalFile(photoPath);
        UI.getCurrent().access(() -> {
            try {
                // 1. Формируем корректный URL
                //String encodedPath = URLEncoder.encode(photoPath, StandardCharsets.UTF_8)
                //        .replace("%2F", "/");

                // 2. Используем HTTP-сервер для доступа к файлам
                //String httpUrl = "http://localhost:8080/" + encodedPath;

                // 3. Открываем в браузере
                Page page = UI.getCurrent().getPage();
                page.executeJs("window.open($0)", photoPath, "popup");



            } catch (Exception e) {
                notifications.show("Ошибка открытия изображения");
            }
        });
    }
    private void getAndSaveImage(String photoPath) {
        try {

            URLConnection connection = new URL(photoPath).openConnection();
            try (InputStream responseStream = connection.getInputStream()) {

                FileStorage fileStorage = fileStorageLocator.getDefault();
                FileRef fileRef = fileStorage.saveStream("photo.jpg", responseStream);


            }
        } catch (IOException e) {
            throw new RuntimeException("Error getting image", e);
        }
    }

    @Autowired
    private FileStorage fileStorage;

    public FileRef uploadFile(String filenameStr) {
        InputStream is = getClass().getResourceAsStream(
                filenameStr
        );
        return fileStorage.saveStream("ФАЙЛ-тест.jpg", is);
    }


    private void saveToLocalFile(String filePath) {
        //FileStorage fileStorage = fileStorageLocator.getDefault();
        //FileRef fileRef = FileRef.fromString("ext::" +filePath);
        //InputStream is = getClass().getResourceAsStream(filePath);
        //FileRef FR = Paths.get(filePath);
// Получаем базовый URL (например, http://localhost:8080)
        //String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        //is = getClass().getResourceAsStream(filePath);
        // Формируем полный URL к статическому ресурсу
        //String fullUrl = baseUrl + "/" + filePath;
        //downloader.download(fileRef);
        try {
            String fileexeurl= "C:/Users/Zver/IdeaProjects/Base6/photos/Комплектующие/Ящик_фруктовый_400х300х180_302-А/Фото_Ящик_фруктовый_400х300х180_302-А.jpg";

            //FileRef FR= FileRef.fromString(filePath);

            //String str2 = fileStorage.toString();
            // Запуск процесса
            Process process = Runtime.getRuntime().exec("cmd.exe /c Start " + fileexeurl);

            // Ожидание завершения процесса (опционально)
            process.waitFor();

            // Вывод кода завершения
            System.out.println("Процесс завершен с кодом: " + process.exitValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openImageInNewTab(String photoPath) {
        // Получаем текущий UI и Page
        UI.getCurrent().access(() -> {
            Page page = UI.getCurrent().getPage();
            String encodedPath = photoPath.replace(" ", "%20");
            page.executeJs("window.open($0, '_blank')", "file:///" + encodedPath);
        });
    }

}