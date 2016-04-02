package net.github.rtc.app.service.generic;

import net.github.rtc.app.dao.generic.GenericDao;
import net.github.rtc.app.model.entity.AbstractPersistenceObject;
import net.github.rtc.app.model.entity.activity.ActivityEntity;
import net.github.rtc.app.model.entity.activity.Auditable;
import net.github.rtc.app.model.event.ActivityEvent;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.model.InitializationError;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public abstract class AbstractCrudEventsServiceTest extends AbstractGenericServiceTest  {

    protected abstract GenericService<AbstractPersistenceObject> getGenericService();
    protected abstract AbstractPersistenceObject getTestEntity();
    protected abstract GenericDao<AbstractPersistenceObject> getGenericDao();

    protected  GenericService<AbstractPersistenceObject> getGrudGenericService() throws InitializationError {
        if(getGenericService() instanceof AbstractCrudEventsService)
        return (AbstractCrudEventsService) getGenericService();
        else throw new InitializationError("Incorrect genericService");
    }
    @Mock
    private ApplicationEventPublisher publisher = new ApplicationEventPublisher() {
        @Override
        public void publishEvent(ApplicationEvent applicationEvent) {
            assertNotNull(applicationEvent);
            assertNotNull(applicationEvent.getSource());
            assertTrue(applicationEvent instanceof ActivityEvent);

            ActivityEvent event = (ActivityEvent) applicationEvent;
            assertTrue(event.getDetails().length()>0);
            assertEquals(event.getEntity(), trueActivityEntity());
            assertNotNull(event.getAction());
        }
    };

    @Test
    public void testDeleteByCode() {
        when(getGenericDao().findByCode(anyString())).thenReturn(getTestEntity());
        super.testDeleteByCode();
    }

    protected abstract ActivityEntity trueActivityEntity();

}