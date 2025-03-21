package com.company.base6.view.statuszayavka;

import com.company.base6.entity.StatusZayavka;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "status-zayavkas/:id", layout = MainView.class)
@ViewController(id = "StatusZayavka.detail")
@ViewDescriptor(path = "status-zayavka-detail-view.xml")
@EditedEntityContainer("statusZayavkaDc")
public class StatusZayavkaDetailView extends StandardDetailView<StatusZayavka> {
}