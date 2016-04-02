package net.github.rtc.app.service.generic;

import net.github.rtc.app.model.entity.AbstractPersistenceObject;
import net.github.rtc.app.model.entity.activity.ActivityAction;
import net.github.rtc.app.model.entity.activity.ActivityEntity;
import net.github.rtc.app.model.entity.activity.Auditable;
import net.github.rtc.app.model.event.ActivityEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;


/**
 * Class that implements commonly used CRUD operations and creates activity events
 * if operation performed successfully
 * @see net.github.rtc.app.model.event.ActivityEvent
 * @param <T>
 */
public abstract class AbstractCrudEventsService<T  extends AbstractPersistenceObject & Auditable>
        extends AbstractGenericServiceImpl<T>  {

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * Persist object to db and creates event about this operations
     * @param object object that needs to be persisted
     * @return persisted object
     */
    @Override
    public T create(T object) {
        final T createdObj = super.create(object);
        createAndPublishEvent(createdObj, ActivityAction.SAVED);
        return createdObj;
    }
    /**
     * Update object in db and creates event about this operations
     * @param object object that needs to be updated
     * @return updated object
     */
    @Override
    public T update(T object) {
        final T updatedObj = super.update(object);
        createAndPublishEvent(updatedObj, ActivityAction.UPDATED);
        return updatedObj;
    }

    /**
     * Remove entity from db by it's code identifier and creates event about this operations
     * @see net.github.rtc.app.model.entity.AbstractPersistenceObject
     * @param code
     */
    @Override
    public void deleteByCode(String code) {
        final T deletedObj = findByCode(code);
        super.deleteByCode(code);
        createAndPublishEvent(deletedObj, ActivityAction.REMOVED);

    }

    /**
     * Creates event about operation
     * @param activity - object that needs to be handled
     * @param action - event type
     */
    private void createAndPublishEvent(Auditable activity,  ActivityAction action) {
        final String detail = activity.getLogDetail();
        final ActivityEntity entity = getActivityEntity();
        final ActivityEvent event = new ActivityEvent(this, detail, entity, action);

        publisher.publishEvent(event);
    }

    /**
     * Get activity entity type
     * @see net.github.rtc.app.model.entity.activity.ActivityEntity
     * @return activity entity that corresponds to selected object
     */
     protected abstract ActivityEntity getActivityEntity();

}
