package net.github.rtc.app.dao.user;

import net.github.rtc.app.dao.generic.GenericDao;
import net.github.rtc.app.model.entity.user.Role;
import net.github.rtc.app.model.entity.user.RoleType;
import net.github.rtc.app.model.entity.user.User;

import java.util.List;

/**
 * Data access object for User domain class
 * @see net.github.rtc.app.model.entity.user.User
 */
public interface UserDao extends GenericDao<User> {

    /**
     * Find user collection by email param
     * @param email email param
     * @return user with correct email
     */
    User findByEmail(String email);

    /**
     * True if user with current email already exists in db
     * @param email email to check
     * @return boolean value
     */
    boolean isEmailExist(String email);

    /**
     * Find Role object in db by it's type
     * @param type the type of role that needs to be found
     * @return role that was found
     */
    Role findRoleByType(RoleType type);

    /**
     * Create role
     * @param role that needs to be created
     * @return created Role
     */
    long createRole(Role role);

    /**
     * Get all users of specified role type
     * @param type of user role of what users must be found
     * @return list of users with specified role
     */
    List<User> findAllByType(RoleType type);
}
