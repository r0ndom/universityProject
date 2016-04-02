package net.github.rtc.app.dao.generic;

import net.github.rtc.app.model.dto.filter.AbstractSearchFilter;
import net.github.rtc.app.model.dto.SearchResults;
import net.github.rtc.app.model.entity.AbstractPersistenceObject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Component
@SuppressWarnings("unchecked")
public abstract class AbstractGenericDaoImpl<T extends AbstractPersistenceObject> implements GenericDao<T> {

    @Autowired
    private SessionFactory sessionFactory;
    private Class<T> type;

    public AbstractGenericDaoImpl() {
        final Type t = getClass().getGenericSuperclass();
        final ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    /**
     * Get current hibernate session
     * @return hibernate session
     */
    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public T create(final T t) {
        getCurrentSession().save(t);
        return t;
    }

    @Override
    public void delete(final long id) {
        getCurrentSession().delete(find(id));
    }

    @Override
    public void deleteByCode(final String code) {
        getCurrentSession().delete(findByCode(code));
    }

    @Override
    public T find(final long id) {
        return (T) getCurrentSession().get(type, id);
    }

    @Override
    public T findByCode(final String code) {
        return (T) getCurrentSession().createCriteria(type).add(Restrictions.eq("code", code)).uniqueResult();
    }

    @Override
    public T update(final T t) {
        getCurrentSession().merge(t);
        return t;
    }

    @Override
    public List<T> findAll() {
        return getCurrentSession().createCriteria(type).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<T> findAll(DetachedCriteria dCriteria) {
        final Criteria criteria = dCriteria.getExecutableCriteria(getCurrentSession());
        return criteria.list();
    }

    @Override
    public SearchResults<T> search(final DetachedCriteria dCriteria, final int start, final int max, Order order) {
        final Criteria criteria = dCriteria.getExecutableCriteria(getCurrentSession());
        final SearchResults<T> results = new SearchResults<>();
        results.getPageModel().setPage(start);
        results.getPageModel().setPerPage(max);
        results.getPageModel().setTotalResults(((Long) criteria.setProjection(Projections.countDistinct("id")).uniqueResult()).intValue());

        criteria.setProjection(null);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setFirstResult((start - 1) * max);
        criteria.setMaxResults(max);
        criteria.addOrder(order);


        results.setResults(criteria.list());
        return results;
    }

    @Override
    public SearchResults<T> search(AbstractSearchFilter searchCommand) {
        return search(searchCommand.getCriteria(), searchCommand.getPage(), searchCommand.getPerPage(), searchCommand.order());
    }
}
