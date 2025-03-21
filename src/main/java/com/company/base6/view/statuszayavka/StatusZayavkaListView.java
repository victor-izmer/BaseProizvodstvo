package com.company.base6.view.statuszayavka;

import com.company.base6.entity.StatusZayavka;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "status-zayavkas", layout = MainView.class)
@ViewController(id = "StatusZayavka.list")
@ViewDescriptor(path = "status-zayavka-list-view.xml")
@LookupComponent("statusZayavkasDataGrid")
@DialogMode(width = "64em")
public class StatusZayavkaListView extends StandardListView<StatusZayavka> {
}