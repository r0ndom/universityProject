package net.github.rtc.app.service.generic;

import net.github.rtc.app.model.dto.filter.AbstractSearchFilter;
import net.github.rtc.app.model.dto.SearchResults;

import java.util.List;

/**
 * Service that must provide CRUD and search operations
 * @param <T>
 */
public interface GenericService<T> {

    /**
     * Remove entity from db by it's code identifier
     * @see net.github.rtc.app.model.entity.AbstractPersistenceObject
     * @param code
     */
    void deleteByCode(String code);

    /**
     * Find entity by it's code identifier
     * @see net.github.rtc.app.model.entity.AbstractPersistenceObject
     * @param code
     */
    T findByCode(String code);

    /**
     * Persist object to db
     * @param object object that needs to be persisted
     * @return persisted object
     */
    T create(T object);

    /**
     * Update object in db
     * @param object object that needs to be updated
     * @return updated object
     */
    T update(T object);

    /**
     * Find all objects of current type in db
     * @return list of current type object
     */
    List<T> findAll();

    /**
     * Search entities in the db with specified params
     * @param searchCommand object that contains search params
     * @see net.github.rtc.app.model.dto.filter.AbstractSearchFilter
     * @return object that contains search results and it's page model
     */
    SearchResults<T> search(AbstractSearchFilter searchCommand);

}
