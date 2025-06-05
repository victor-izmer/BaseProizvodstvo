package com.company.base6.view.specification;



import com.company.base6.entity.*;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.CellFocusEvent;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.grid.dnd.GridDragEndEvent;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.treegrid.CollapseEvent;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import io.jmix.core.*;
import io.jmix.core.validation.group.UiCrossFieldChecks;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.action.SecuredBaseAction;
import io.jmix.flowui.action.list.CreateAction;
import io.jmix.flowui.action.list.EditAction;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.grid.TreeDataGrid;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.component.validation.ValidationErrors;
import io.jmix.flowui.download.DownloadDataProvider;
import io.jmix.flowui.download.DownloadFormat;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.kit.action.Action;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.*;
import io.jmix.flowui.upload.TemporaryStorage;
import io.jmix.flowui.view.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import io.jmix.flowui.Notifications;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.processing.Filer;


@Route(value = "specifications", layout = MainView.class)
@ViewController(id = "Specification.list")
@ViewDescriptor(path = "specification-list-view.xml")
@LookupComponent("specificationsDataGrid")
@DialogMode(width = "64em")

public class SpecificationListView extends StandardListView<Specification> {
    @Autowired
    private UiComponents uiComponents;

    @ViewComponent
    private DataContext dataContext;

    @ViewComponent
    private CollectionContainer<Specification> specificationsDc;

    @ViewComponent
    private InstanceContainer<Specification> specificationDc;

    @ViewComponent
    private InstanceLoader<Specification> specificationDl;

    @ViewComponent
    private VerticalLayout listLayout;

    @ViewComponent
    private TreeDataGrid<Specification> specificationsDataGrid;

    @ViewComponent
    private FormLayout form;

    @ViewComponent
    private HorizontalLayout detailActions;
    @ViewComponent
    private CollectionLoader<Specification> specificationDetalDl;
    @ViewComponent
    private CollectionContainer<Specification> specificationDetalDc;
    private int rowIndex;
    @Autowired
    private Metadata metadata;
    @ViewComponent("childSpecification.edit")
    private EditAction<Specification> childSpecificationEdit;
    @ViewComponent("childSpecification.create")
    private CreateAction<Specification> childSpecificationCreate;

    private double scrollPosition = 0;
    @ViewComponent
    private HorizontalLayout formBox;
    @ViewComponent
    private CollectionLoader<Specification> specificationsDl;

    @Subscribe
    public void onInit(final InitEvent event) {
        specificationsDataGrid.getActions().forEach(action -> {
            if (action instanceof SecuredBaseAction secured) {
                secured.addEnabledRule(() -> listLayout.isEnabled());
            }
        });

    // Отслеживаем событие выбора элемента в TreeDataGrid
    specificationsDataGrid.addCellFocusListener(selectionEvent -> {
        if (selectionEvent.getItem().isPresent()) {
            Specification selectedSpec = selectionEvent.getItem().orElse(null);
            onSpecificationsDataGridCellFocus(selectedSpec);
        }
    });
    // сохранением позиции скролла
        specificationsDataGrid.addSelectionListener(selectionEvent -> {
            // Сохраняем текущую позицию скролла перед обновлением
            scrollPosition = specificationsDataGrid.getElement().getProperty("_scrollTop", 0.0);
        });

        // Обновляем позицию скролла после рендеринга
        specificationsDataGrid.addCellFocusListener(updaterEvent -> {
            specificationsDataGrid.scrollToEnd();
        });
        
    }


    @ViewComponent
    private DataGrid<Specification> childSpecification;
    @Subscribe("childSpecification.create")
    public void onchildSpecificationCreate(final ActionPerformedEvent event) {
        Specification parentSpec = specificationsDataGrid.getSingleSelectedItem();
        if (parentSpec != null) {
            Specification child = metadata.create(Specification.class);
            child.setParentref(parentSpec);

            // Добавить в контейнер и выбрать
            specificationDetalDc.getMutableItems().add(child);
            childSpecification.select(child);
            dataContext.merge(child);

            // Открыть редактор
            childSpecificationEdit.execute();
                // Обновляем контейнер
                specificationDetalDl.load();
                specificationsDl.load();

        }
    }

//    @Subscribe("specificationsDataGrid.edit")
//    public void onSpecificationsDataGridEdit(final ActionPerformedEvent event) {
//        updateControls(true);
//    }
//
//    @Subscribe("saveButton")
//    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
//        Specification item = specificationDc.getItem();
//        ValidationErrors validationErrors = validateView(item);
//        if (!validationErrors.isEmpty()) {
//            ViewValidation viewValidation = getViewValidation();
//            viewValidation.showValidationErrors(validationErrors);
//            viewValidation.focusProblemComponent(validationErrors);
//            return;
//        }
//        dataContext.save();
//        specificationsDc.replaceItem(item);
//        updateControls(false);
//    }
//
//    @Subscribe("cancelButton")
//    public void onCancelButtonClick(final ClickEvent<JmixButton> event) {
//        dataContext.clear();
//        specificationDc.setItem(null);
//        specificationDl.load();
//        updateControls(false);
//    }

    @Subscribe(id = "specificationsDc", target = Target.DATA_CONTAINER)
    public void onSpecificationsDcItemChange(final InstanceContainer.ItemChangeEvent<Specification> event) {
        Specification entity = event.getItem();
        dataContext.clear();
        if (entity != null) {
            specificationDl.setEntityId(entity.getId());
            specificationDl.load();
        } else {
            specificationDl.setEntityId(null);
            specificationDc.setItem(null);
        }
        updateControls(false);
    }

    protected ValidationErrors validateView(Specification entity) {
        ViewValidation viewValidation = getViewValidation();
        ValidationErrors validationErrors = viewValidation.validateUiComponents(formBox);
        if (!validationErrors.isEmpty()) {
            return validationErrors;
        }
        validationErrors.addAll(viewValidation.validateBeanGroup(UiCrossFieldChecks.class, entity));
        return validationErrors;
    }

    private void updateControls(boolean editing) {

        UiComponentUtils.getComponents(formBox).forEach(component -> {
            if (component instanceof HasValueAndElement<?, ?> field) {
                field.setReadOnly(!editing);
            }
        });


        //detailActions.setVisible(editing);
        //listLayout.setEnabled(!editing);
        specificationsDataGrid.getActions().forEach(Action::refreshState);

    }

    private ViewValidation getViewValidation() {
        return getApplicationContext().getBean(ViewValidation.class);
    }

    @Subscribe("specificationsDataGrid")
    public void onSpecificationsDataGridCellFocus(Specification selectedSpec) {
        //if (selectedSpec != null && isParentSpecification(selectedSpec)) {
            // Если выбрана родительская деталь, загружаем дочерние элементы
            specificationDetalDl.setParameter("detal", selectedSpec.getId());
            specificationDetalDl.load();
        //} else {
            // Очищаем таблицу, если выбрана обычная деталь
        //    specificationDetalDl.setParameter("detal", selectedSpec.getId());
            // specificationDetalDl.removeParameter("detal");
        //    specificationDetalDl.load();
        //}

    }

    @Subscribe("specificationsDataGrid")
    public void onSpecificationsDataGridItemClick(final ItemClickEvent<Specification> event) {
        //specificationsDataGrid.scrollToIndex(specificationsDataGrid.getSelectedItems());

    }

    @Autowired
    private FileStorageLocator fileStorageLocator;





    @Autowired
    private FileStorage fileStorage;

    @Supply(to = "childSpecification.[detalref.typeref.picture]", subject = "renderer")
    private Renderer<Specification> childSpecificationDetalrefTyperefPictureRenderer() {
        return new ComponentRenderer<>(specification -> {
            FileRef fileRef = specification.getDetalref().getTyperef().getPicture();
            if (fileRef != null) {
                Image image = uiComponents.create(Image.class);
                image.setWidth("30px");
                image.setHeight("30px");
                StreamResource streamResource = new StreamResource(
                        fileRef.getFileName(),
                        () -> fileStorage.openStream(fileRef));
                image.setSrc(streamResource);
                image.setClassName("user-picture");

                return image;
            } else {
                return null;
            }
        });
    }

//    @Supply(to = "childSpecification.[detalref.priceLastNDS]", subject = "renderer")
//    private Renderer<Specification> childSpecificationDetalrefPriceLastNDSRenderer() {
//        return new ComponentRenderer<>(
//                () -> {
//                    JmixCheckbox checkbox = uiComponents.create(JmixCheckbox.class);
//                    checkbox.setReadOnly(true);
//                    return checkbox;
//                },
//                (checkbox, specification) -> checkbox.setValue(specification.getDetalref().getPriceLastNDS())
//        );
//    }


}