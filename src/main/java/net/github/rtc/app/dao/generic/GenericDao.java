package net.github.rtc.app.dao.generic;

import net.github.rtc.app.model.dto.filter.AbstractSearchFilter;
import net.github.rtc.app.model.dto.SearchResults;
import net.github.rtc.app.model.entity.AbstractPersistenceObject;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * Data access object for any domain classes
 * @see net.github.rtc.app.model.entity.AbstractPersistenceObject
 * @param <T>  the type parameter
 */
public interface GenericDao<T extends AbstractPersistenceObject> {

    /**
     * Persist object to database.
     *
     * @param t the object that must be persisted
     * @return persisted object
     */
    T create(T t);

    /**
     * Remove object from db
     *
     * @param id the id of object to remove
     */
    void delete(long id);

    /**
     * Find object in db by id
     *
     * @param id the id of the project
     * @return found object
     */
    T find(long id);

    /**
     * Update object in database.
     *
     * @param t the object that needs to be updated
     * @return updated object
     */
    T update(T t);

    /**
     * Find all objects of current type in db.
     *
     * @return the list of objects
     */
    List<T> findAll();

    /**
     * Delete entity by code.
     *
     * @param code the code
     */
    void deleteByCode(String code);

    /**
     * Find entity by code.
     *
     * @param code the code
     * @return founded entity
     */
    T findByCode(String code);

    /**
     * Find all objects in db by criteria.
     *
     * @param criteria the criteria
     * @return the list of objects
     */
    List<T> findAll(DetachedCriteria criteria);

    /**
     * Find fixed size of objects with some criteria and additional search info
     *
     * @param criteria the criteria search params
     * @param start from what result
     * @param max to what
     * @param order order in which objects are sorted
     * @return the search results means object that contains founded objects
     * and additional information about performed search
     */
    SearchResults<T> search(DetachedCriteria criteria, int start, int max, Order order);

    /**
     * Search search results.
     *
     * @param searchCommand object that contains information about search operation
     * @return the search results means object that contains founded objects
     * and additional information about performed search
     */
    SearchResults<T> search(AbstractSearchFilter searchCommand);

}
