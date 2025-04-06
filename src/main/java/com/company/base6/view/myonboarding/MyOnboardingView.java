package com.company.base6.view.myonboarding;


import com.company.base6.entity.User;
import com.company.base6.entity.UserStep;
import com.company.base6.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Route(value = "my-onboarding-view", layout = MainView.class)
@ViewController(id = "MyOnboardingView")
@ViewDescriptor(path = "my-onboarding-view.xml")
public class MyOnboardingView extends StandardView {
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent
    private CollectionLoader<UserStep> userStepsDl;
    @Autowired
    private UiComponents uiComponents;

    @ViewComponent
    private Span completedStepsLabel;

    @ViewComponent
    private Span overdueStepsLabel;
    @ViewComponent
    private Span totalStepsLabel;
    @ViewComponent
    private CollectionContainer<UserStep> userStepsDc;
    @ViewComponent
    private DataContext dataContext;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        final User user = (User) currentAuthentication.getUser();
        userStepsDl.setParameter("user",user);
        userStepsDl.load();
        updateLabels();
    }

    @Supply(to = "userStepsDataGrid.completed", subject = "renderer")
    private Renderer<UserStep> userStepsDataGridCompletedRenderer() {
        return new ComponentRenderer<>(userStep -> {
            Checkbox checkbox = uiComponents.create(Checkbox.class);
            checkbox.setValue(userStep.getCompleteDate() != null);
            checkbox.addValueChangeListener(e -> {
                if (userStep.getCompleteDate() == null) {
                    userStep.setCompleteDate(LocalDate.now());
                } else {
                    userStep.setCompleteDate(null);
                }
            });
            return checkbox;
        });
    }
    private void updateLabels() {
        totalStepsLabel.setText("Total steps: " + userStepsDc.getItems().size());

        long completedCount = userStepsDc.getItems().stream()
                .filter(us -> us.getCompleteDate() != null)
                .count();
        completedStepsLabel.setText("Completed steps: " + completedCount);

        long overdueCount = userStepsDc.getItems().stream()
                .filter(us -> isOverdue(us))
                .count();
        overdueStepsLabel.setText("Overdue steps: " + overdueCount);
    }

    @Subscribe(id = "userStepsDc", target = Target.DATA_CONTAINER)
    public void onUserStepsDcItemPropertyChange(final InstanceContainer.ItemPropertyChangeEvent<UserStep> event) {
        updateLabels();
    }

    private boolean isOverdue(UserStep us) {
        return us.getCompleteDate() == null
                && us.getDueDate() != null
                && us.getDueDate().isBefore(LocalDate.now());
    }

    @Subscribe(id = "saveButton", subject = "clickListener")
    public void onSaveButtonClick(final ClickEvent<JmixButton> event) {
        dataContext.save();
        close(StandardOutcome.SAVE);
    }

    @Subscribe(id = "discardButton", subject = "clickListener")
    public void onDiscardButtonClick(final ClickEvent<JmixButton> event) {
        close(StandardOutcome.DISCARD);
    }

    @Install(to = "userStepsDataGrid.dueDate", subject = "partNameGenerator")
    private String userStepsDataGridDueDatePartNameGenerator(final UserStep userStep) {
        return isOverdue(userStep) ? "overdue-step" : null;
    }
}