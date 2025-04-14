package com.company.base6.listener;

import com.company.base6.entity.StatusExecute;
import com.company.base6.entity.User;
import com.company.base6.entity.UserStep;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.event.EntityChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class UserStepEventListener {

    @Autowired
    private DataManager dataManager;

    @EventListener
    public void onUserStepChangedBeforeCommit(EntityChangedEvent<UserStep> event) {
        User user;
        if (event.getType() != EntityChangedEvent.Type.DELETED) {
            Id<UserStep> userStepId = event.getEntityId();
            UserStep userStep = dataManager.load(userStepId).one();
            user = userStep.getUser();
        } else {
            Id<User> userId = event.getChanges().getOldReferenceId("user");
            if (userId == null) {
                throw new IllegalStateException("Нет сотрудника в удаленном спсике/Cannot get User from deleted UserStep");
            }
            user = dataManager.load(userId).one();
        }

        long completedCount = user.getSteps().stream()
                .filter(us -> us.getCompleteDate() != null)
                .count();
        if (completedCount == 0) {
            user.setStatusExeRef(StatusExecute.CREATE);
        } else if (completedCount == user.getSteps().size()) {
            user.setStatusExeRef(StatusExecute.WORK_OUT);
        } else {
            user.setStatusExeRef(StatusExecute.IN_WORK);
        }

        dataManager.save(user);
    }
}