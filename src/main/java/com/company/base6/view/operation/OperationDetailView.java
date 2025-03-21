package com.company.base6.view.operation;

import com.company.base6.entity.Operation;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "operations/:id", layout = MainView.class)
@ViewController(id = "Operation.detail")
@ViewDescriptor(path = "operation-detail-view.xml")
@EditedEntityContainer("operationDc")
public class OperationDetailView extends StandardDetailView<Operation> {
}