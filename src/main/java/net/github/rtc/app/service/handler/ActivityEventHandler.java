package net.github.rtc.app.service.handler;

import net.github.rtc.app.model.entity.activity.Activity;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.service.activity.ActivityService;
import net.github.rtc.app.service.date.DateService;
import net.github.rtc.app.service.security.AuthorizedUserProvider;
import net.github.rtc.app.model.event.ActivityEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Class that listen to activity events and handles them
 * @see net.github.rtc.app.model.event.ActivityEvent
 */
@Component
public class ActivityEventHandler implements ApplicationListener<ActivityEvent> {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private DateService dateService;

    /**
     * Catch activity event and persist info abut it to db
     * @param event activity that was published (new user created for example)
     */
    @Override
    public void onApplicationEvent(ActivityEvent event) {
        final User user = AuthorizedUserProvider.getAuthorizedUser();
        final Activity activity = new Activity();

        activity.setUsername(user.getSurnameName());
        activity.setDetail(event.getDetails());
        activity.setAction(event.getAction());
        activity.setActionDate(dateService.getCurrentDate());
        activity.setEntity(event.getEntity());

        activityService.create(activity);
    }
}
