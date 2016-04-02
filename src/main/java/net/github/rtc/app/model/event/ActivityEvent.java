package net.github.rtc.app.model.event;

import net.github.rtc.app.model.entity.activity.ActivityAction;
import net.github.rtc.app.model.entity.activity.ActivityEntity;
import org.springframework.context.ApplicationEvent;


public class ActivityEvent extends ApplicationEvent {

    private String details;

    private ActivityEntity entity;

    private ActivityAction action;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public ActivityEvent(Object source, String details, ActivityEntity entity, ActivityAction action) {
        super(source);
        this.details = details;
        this.entity = entity;
        this.action = action;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ActivityEntity getEntity() {
        return entity;
    }

    public void setEntity(ActivityEntity entity) {
        this.entity = entity;
    }

    public ActivityAction getAction() {
        return action;
    }

    public void setAction(ActivityAction action) {
        this.action = action;
    }
}

