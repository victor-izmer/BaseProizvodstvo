package com.company.base6.view.typesupplier;

import com.company.base6.entity.TypeSupplier;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "type-suppliers/:id", layout = MainView.class)
@ViewController(id = "TypeSupplier.detail")
@ViewDescriptor(path = "type-supplier-detail-view.xml")
@EditedEntityContainer("typeSupplierDc")
public class TypeSupplierDetailView extends StandardDetailView<TypeSupplier> {
}