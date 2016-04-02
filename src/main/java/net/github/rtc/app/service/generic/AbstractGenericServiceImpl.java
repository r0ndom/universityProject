package net.github.rtc.app.service.generic;

import net.github.rtc.app.dao.generic.GenericDao;
import net.github.rtc.app.model.entity.AbstractPersistenceObject;
import net.github.rtc.app.model.dto.filter.AbstractSearchFilter;
import net.github.rtc.app.model.dto.SearchResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class that implements commonly used CRUD and search operations
 * @param <T>
 */
@Transactional
public abstract class AbstractGenericServiceImpl<T extends AbstractPersistenceObject> implements GenericService<T> {

    @Autowired
    private CodeGenerationService codeGenerationService;

    /**
     * Get specified dao implementation for current service
     * @see net.github.rtc.app.dao.generic.AbstractGenericDaoImpl
     * @return object that directly manages db operations
     */
    abstract protected GenericDao<T> getDao();

    /**
     * Generate code for object that needs to be persisted
     * @see net.github.rtc.app.model.entity.AbstractPersistenceObject
     * @return string code unique identifier
     */
    protected String getCode() {
        String code = codeGenerationService.generate();
        while (true) {
            if (!isCodeExist(code)) {
                return code;
            }
            code = codeGenerationService.generate();
        }
    }

    @Override
    public void deleteByCode(String code) {
        getDao().deleteByCode(code);
    }

    @Override
    public T findByCode(String code) {
        return getDao().findByCode(code);
    }

    @Override
    public T create(T t) {
        t.setCode(getCode());
        return getDao().create(t);
    }

    @Override
    public T update(T t) {
        return getDao().update(t);
    }

    @Override
    public List<T> findAll() {
        return getDao().findAll();
    }

    @Override
    public SearchResults<T> search(AbstractSearchFilter searchCommand) {

        return getDao().search(searchCommand);
    }

    /**
     * Return true if entity with current code already exists in db
     * @param code the code that needs to be checked
     * @return boolean value
     */
    private boolean isCodeExist(String code) { return getDao().findByCode(code) != null; }
}
