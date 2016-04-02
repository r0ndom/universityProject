package net.github.rtc.app.dao.user;

import net.github.rtc.app.dao.generic.AbstractGenericDaoImpl;
import net.github.rtc.app.model.entity.user.Role;
import net.github.rtc.app.model.entity.user.RoleType;
import net.github.rtc.app.model.entity.user.User;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserDaoImpl extends AbstractGenericDaoImpl<User> implements UserDao {

    public static final String EMAIL_STRING = "email";

    @Override
    public User findByEmail(final String email) {
        return (User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq(EMAIL_STRING, email)).uniqueResult();
    }

    @Override
    public boolean isEmailExist(String email) {
        final int count = ((Long) getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq(EMAIL_STRING, email))
                .setProjection(Projections.rowCount()).uniqueResult()).intValue();
        return count != 0;
    }

    @Override
    public Role findRoleByType(final RoleType type) {
        return (Role) getCurrentSession().createCriteria(Role.class).add(Restrictions.eq("name", type)).uniqueResult();
    }

    @Override
    public long createRole(Role role) {
        return  (long) getCurrentSession().save(role);
    }

    @Override
    public List<User> findAllByType(final RoleType type) {
        return getCurrentSession().createCriteria(User.class).createAlias("authorities", "a").add(Restrictions.eq("a.name", type)).list();
    }
}
