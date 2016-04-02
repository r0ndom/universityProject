package net.github.rtc.app.dao.user;

import net.github.rtc.app.model.entity.user.UserConnection;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.*;

@Repository
public class UserConnectionDaoImpl implements UserConnectionDao {

    private static final String USER_ID = "userId";
    private static final String PROVIDER_ID = "providerId";
    private static final String PROVIDER_USER_ID = "providerUserId";

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<String> findUserIdsWithConnection(String providerId, String providerUserId) {
        final Criteria criteria =  getCurrentSession().createCriteria(UserConnection.class);
        criteria.add(Restrictions.eq(PROVIDER_ID, providerId));
        criteria.add(Restrictions.eq(PROVIDER_USER_ID, providerUserId));
        criteria.setProjection(Projections.property(USER_ID));
        return criteria.list();
    }

    @Override
    public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
        final Criteria criteria =  getCurrentSession().createCriteria(UserConnection.class);
        criteria.add(Restrictions.eq(PROVIDER_ID, providerId));
        criteria.add(Restrictions.in(PROVIDER_USER_ID, providerUserIds));
        criteria.setProjection(Projections.property(USER_ID));
        return new HashSet<>(criteria.list());
    }

    @Override
    public List<UserConnection> get(String userId) {
        final Criteria criteria =  getCurrentSession().createCriteria(UserConnection.class);
        criteria.add(Restrictions.eq(USER_ID, userId));
        return criteria.list();
    }

    @Override
    public List<UserConnection> get(String userId, String providerId) {
        final Criteria criteria =  getCurrentSession().createCriteria(UserConnection.class);
        criteria.add(Restrictions.eq(USER_ID, userId));
        criteria.add(Restrictions.eq(PROVIDER_ID, providerId));
        return criteria.list();
    }

    @Override
    @Transactional
    public UserConnection get(String userId, String providerId, String providerUserId) {
        final Criteria criteria =  getCurrentSession().createCriteria(UserConnection.class);
        criteria.add(Restrictions.eq(USER_ID, userId));
        criteria.add(Restrictions.eq(PROVIDER_ID, providerId));
        criteria.add(Restrictions.eq(PROVIDER_USER_ID, providerUserId));
        return (UserConnection) criteria.list().get(0);
    }

    @Override
    public List<UserConnection> getAll(String userId, MultiValueMap<String, String> providerUsers) {
        final List<UserConnection> userList = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : providerUsers.entrySet()) {
            final Criteria criteria =  getCurrentSession().createCriteria(UserConnection.class);
            criteria.add(Restrictions.eq(USER_ID, userId));
            criteria.add(Restrictions.eq(PROVIDER_ID, userId));
            criteria.add(Restrictions.in(PROVIDER_USER_ID,  entry.getValue()));
            userList.addAll(criteria.list());
        }
        return userList;
    }

    @Override
    public List<UserConnection> getPrimary(String userId, String providerId) {
        return get(userId, providerId);
    }

    @Override
    public int getRank(String userId, String providerId) {
        final String hql = "select max(u.rank) from UserConnection u where userId= :userId and providerId= :providerId";
        final Integer rank = (Integer) getCurrentSession().createQuery(hql).setString(USER_ID, userId).setString(PROVIDER_ID, providerId).uniqueResult();
        return rank == null ? 1 : (rank + 1);
    }

    @Override
    public void remove(String userId, String providerId) {
        for (UserConnection connection: get(userId, providerId)) {
            getCurrentSession().delete(connection);
        }
    }

    @Override
    public void remove(String userId, String providerId, String providerUserId) {
        getCurrentSession().delete(get(userId, providerId, providerUserId));
    }

    @Override
    public String createUserConnection(UserConnection userConnection) {
        return (String) getCurrentSession().save(userConnection);
    }


    @Override
    @Transactional
    public UserConnection updateUserConnection(UserConnection userConnection) {
        return (UserConnection) getCurrentSession().merge(userConnection);
    }

    @Override
    public UserConnection findById(String id) {
        final Criteria criteria =  getCurrentSession().createCriteria(UserConnection.class);
        criteria.add(Restrictions.eq(USER_ID, id));
        return (UserConnection) criteria.uniqueResult();
    }

    @Override
    public UserConnection findByUsername(String name) {
        final Criteria criteria =  getCurrentSession().createCriteria(UserConnection.class);
        criteria.add(Restrictions.eq("displayName", name));
        return (UserConnection) criteria.uniqueResult();
    }
}
