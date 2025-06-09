package com.company.base6.view.workpiece;


import com.company.base6.entity.Specification;
import com.company.base6.entity.Workpiece;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.grid.TreeDataGrid;
import io.jmix.flowui.view.*;

import java.util.ArrayList;
import java.util.List;

@Route(value = "workpieces", layout = MainView.class)
@ViewController(id = "Workpiece.list")
@ViewDescriptor(path = "workpiece-list-view.xml")
@LookupComponent("workpiecesDataGrid")
@DialogMode(width = "100em", height = "60em")
public class WorkpieceListView extends StandardListView<Workpiece> {

    @ViewComponent
    private TreeDataGrid<Specification> specificationsDataGrid;

    protected Specification selectedSpecification;

    @Subscribe
    public void onReady(ReadyEvent event) {
        if (selectedSpecification != null) {
            specificationsDataGrid.expand(getParentSpecificationsToExpand(selectedSpecification));
            specificationsDataGrid.select(selectedSpecification);
        }
    }

    public void setSelectedSpecification(Specification specification) {
        this.selectedSpecification = specification;
    }

    private List<Specification> getParentSpecificationsToExpand(Specification specification) {
        ArrayList<Specification> specificationToExpand = new ArrayList<>();
        specificationToExpand.add(specification);

        Specification currentSpecification = specification;
        while (currentSpecification.getParentref() != null) {
            currentSpecification = currentSpecification.getParentref();
            specificationToExpand.add(currentSpecification);
        }

        return specificationToExpand;
    }
}