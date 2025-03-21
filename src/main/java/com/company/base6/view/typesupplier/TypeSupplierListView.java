package com.company.base6.view.typesupplier;

import com.company.base6.entity.TypeSupplier;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "type-suppliers", layout = MainView.class)
@ViewController(id = "TypeSupplier.list")
@ViewDescriptor(path = "type-supplier-list-view.xml")
@LookupComponent("typeSuppliersDataGrid")
@DialogMode(width = "64em")
public class TypeSupplierListView extends StandardListView<TypeSupplier> {
}