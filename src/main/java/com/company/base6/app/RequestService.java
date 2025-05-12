package com.company.base6.app;

import com.company.base6.entity.Request;
import io.jmix.core.DataManager;
import io.jmix.flowui.Notifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RequestService {
    private final DataManager dataManager;

    public RequestService(DataManager dataManager) {
        this.dataManager = dataManager;
    }
    @Autowired
    private Notifications notifications;
    public void notifyOverDue(Request request){
        dataManager.load(Request.class)
                .query("select r from Request r")
                .list().forEach(r->{
                    if(r.getDateControl()!=null && r.getDateControl().isBefore(LocalDate.now())){
                        notifications.show("Есть просроченные задачи");
                    }
                });
    }
}