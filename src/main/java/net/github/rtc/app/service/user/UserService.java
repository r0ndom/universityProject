package net.github.rtc.app.service.user;

import net.github.rtc.app.model.entity.user.Role;
import net.github.rtc.app.model.entity.user.RoleType;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.service.generic.GenericService;
import net.github.rtc.app.service.generic.ModelService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.Connection;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * The service class that is responsible for operation on User model class
 * @see net.github.rtc.app.model.entity.user.User
 */
public interface UserService extends ModelService<User>, UserDetailsService, GenericService<User> {

    /**
     * Create new user role and persist it to db
     * @param type role type
     */
    long createRole(RoleType type);

    /**
     * Get role from db by it's type
     * @param type type of the role
     * @return role object
     */
    Role findRoleByType(RoleType type);

    /**
     * Find all users with specified authority
     * @param type type of user role
     * @return list of users with specified authority
     */
    List<User> findAllByRole(RoleType type);

    /**
     * Get user by username that is user's email
     * @param email user's username
     * @return user with specific email
     */
    User loadUserByUsername(String email);

    /**
     * Create user and persist to db
     * @param user user that will be created
     * @param image user's photo
     * @param isActive true if user status needs to be active
     * @return created user
     */
    User create(User user, MultipartFile image, boolean isActive);


    /**
     * Update user and persist to db
     * @param user user that will be created
     * @param image user's photo
     * @param isActive true if user status needs to be active
     * @return updated user
     */
    User update(User user, MultipartFile image, boolean isActive);

    /**
     * Register user using social account (facebook etc)
     * @param connection connection to service provider
     * @return created user
     */
    User createWithSocial(Connection<?> connection);

    /**
     * Set user status FOR_REMOVALand set removal date
     * @param userCode code of user that needs to be marked for removal
     */
    void markUserForRemoval(String userCode);

    /**
     * If user status was set FOR_REMOVAL set it to INACTIVE and remove removal date
     * @param userCode code of user that needs to be restored
     */
    void restoreUser(String userCode);

    /**
     * Set user status as ACTIVE
     * @param userCode code of user that needs to be activated
     */
    void activateUser(String userCode);

    /**
     * Set user status as INACTIVE
     * @param userCode code of user that needs to be deactivated
     */
    void inactivateUser(String userCode);

    /**
     * Returns map in which key is username value is user code. Search is performed by role type
     * @param roleType user of what role will be put to map
     * @return resulting map
     */
    Map<String, String> findAllUserNameAndCode(RoleType roleType);

    /**
     * Register user means set status ACTIVE set role USER set register date as current and persist to db
     * @param user user that needs to be registered
     * @param img user avatar
     */
    void registerUser(User user, MultipartFile img);

    /**
     * Return true if user with current email exists
     * @param email email to check
     * @return boolean value
     */
    boolean isEmailExist(String email);
}
