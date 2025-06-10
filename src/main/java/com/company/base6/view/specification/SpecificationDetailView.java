package com.company.base6.view.specification;


import com.company.base6.entity.Operation;
import com.company.base6.entity.Specification;
import com.company.base6.entity.Workpiece;
import com.company.base6.view.main.MainView;
import com.company.base6.view.workpiece.WorkpieceListView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.Route;
import io.jmix.core.MetadataTools;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.view.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "specifications/:id", layout = MainView.class)
@ViewController(id = "Specification.detail")
@ViewDescriptor(path = "specification-detail-view.xml")
@EditedEntityContainer("specificationDc")
public class SpecificationDetailView extends StandardDetailView<Specification> {

    @ViewComponent
    private EntityComboBox<Workpiece> workpiecesComboBox2;
    @Autowired
    private MetadataTools metadataTools;

    @Subscribe
    public void onReady(ReadyEvent event) {
        String detailRefInstanceName = metadataTools.getInstanceName(getEditedEntity().getDetalref().getTyperef());
        workpiecesComboBox2.setLabel(detailRefInstanceName);
    }

    @Subscribe(id = "photo", subject = "clickListener")
    public void onPhotoClick(final ClickEvent<JmixImage<?>> event) {
        UI.getCurrent().getPage().open(event.getSource().getSrc());
    }

    @Subscribe(id = "graph", subject = "clickListener")
    public void onGraphClick(final ClickEvent<JmixImage<?>> event) {
        UI.getCurrent().getPage().open(event.getSource().getSrc());
    }

    @Install(to = "workpiecesComboBox2.entityLookup", subject = "viewConfigurer")
    private void workpiecesComboBox2EntityLookupViewConfigurer(final WorkpieceListView view) {
        view.setSelectedSpecification(getEditedEntity().getParentref());
    }

    @Subscribe
    public void onBeforeSave(final BeforeSaveEvent event) {
        Workpiece detalref = getEditedEntity().getDetalref();

        if (detalref == null || !CollectionUtils.isNotEmpty(detalref.getOperationRef())) {
            return;
        }

        List<Operation> operations = detalref.getOperationRef();
        Float priceDetailOperation = 0f;
        for (Operation operation : operations) {
            priceDetailOperation += operation.getPriceOp();
        }

        getEditedEntity().setPriceDetalOperation(priceDetailOperation);
    }


}