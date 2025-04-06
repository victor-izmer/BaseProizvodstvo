package com.company.base6.view.specification;


import com.company.base6.entity.Specification;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "specifications/:id", layout = MainView.class)
@ViewController(id = "Specification.detail")
@ViewDescriptor(path = "specification-detail-view.xml")
@EditedEntityContainer("specificationDc")
public class SpecificationDetailView extends StandardDetailView<Specification> {

}