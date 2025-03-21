package com.company.base6.view.supplier;

import com.company.base6.entity.Supplier;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "suppliers/:id", layout = MainView.class)
@ViewController(id = "Supplier.detail")
@ViewDescriptor(path = "supplier-detail-view.xml")
@EditedEntityContainer("supplierDc")
public class SupplierDetailView extends StandardDetailView<Supplier> {
}