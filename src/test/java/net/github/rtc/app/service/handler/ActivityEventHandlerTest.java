package net.github.rtc.app.service.handler;

import net.github.rtc.app.model.entity.activity.Activity;
import net.github.rtc.app.model.entity.activity.ActivityAction;
import net.github.rtc.app.model.entity.activity.ActivityEntity;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.model.event.ActivityEvent;
import net.github.rtc.app.service.activity.ActivityService;
import net.github.rtc.app.service.date.DateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ActivityEventHandlerTest {

    @InjectMocks
    private ActivityEventHandler eventHandler;
    @Mock
    private ActivityService activityService;
    @Mock
    private DateService dateService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Authentication auth = new UsernamePasswordAuthenticationToken(new User() , null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    public void testHandle() {
        eventHandler.onApplicationEvent(
                new ActivityEvent(new Object(), "detail", ActivityEntity.COURSE, ActivityAction.SAVED));
        verify(activityService, times(1)).create(any(Activity.class));
    }
}
