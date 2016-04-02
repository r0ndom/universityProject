package net.github.rtc.app.service.user;

import net.github.rtc.app.dao.generic.GenericDao;
import net.github.rtc.app.dao.user.UserDao;
import net.github.rtc.app.model.entity.activity.ActivityEntity;
import net.github.rtc.app.model.entity.user.Role;
import net.github.rtc.app.model.entity.user.RoleType;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.model.entity.user.UserStatus;
import net.github.rtc.app.service.builder.MailMessageBuilder;
import net.github.rtc.app.service.date.DateService;
import net.github.rtc.app.service.generic.AbstractCrudEventsService;
import net.github.rtc.app.service.mail.MailService;
import net.github.rtc.app.service.social.user.SocialUserProvider;
import net.github.rtc.app.utils.web.files.upload.FileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends AbstractCrudEventsService<User> implements UserService {

    private static final int USER_REMOVAL_DELAY = 3;
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class.getName());
    @Autowired
    private UserDao userDao;
    @Autowired
    private DateService dateService;
    @Autowired
    private FileUpload upload;
    @Autowired
    private MailService mailService;
    @Autowired
    private MailMessageBuilder mailMessageBuilder;
    @Autowired
    private SocialUserProvider socialUserProvider;

    @Override
    protected GenericDao<User> getDao() {
        return userDao;
    }

    @Override
    @Nonnull
    public User create(final User user) {
        user.setRegisterDate(dateService.getCurrentDate());
        super.create(user);
        return user;
    }

    @Override
    @Nonnull
    public User create(User user, MultipartFile image, boolean isActive) {
        setStatusAndImage(user, image, isActive);
        return create(user);
    }

    @Override
    @Nonnull
    public User update(User user, MultipartFile image, boolean isActive) {
        setStatusAndImage(user, image, isActive);
        return update(user);
    }

    @Override
    @Nonnull
    public User createWithSocial(Connection<?> connection) {
        final User newUser = socialUserProvider.getUser(connection);
        newUser.setCode(getCode());
        newUser.setAuthorities(Arrays.asList(this.findRoleByType(RoleType.ROLE_USER)));
        newUser.setAccountNonExpired(true);
        newUser.setAccountNonLocked(true);
        newUser.setStatus(UserStatus.ACTIVE);
        newUser.setRegisterDate(dateService.getCurrentDate());
        userDao.create(newUser);
        return newUser;
    }

    /**
     * Set user photo path and upload photo to server also optionally set ACTIVE status
     * @param user for what user operation will be performed
     * @param image user's new photo
     * @param isActive is it necessary to set status ACTIVE
     */
    private void setStatusAndImage(User user, MultipartFile image, boolean isActive) {
        if (isActive) {
            user.setStatus(UserStatus.ACTIVE);
        }
        if (!image.isEmpty()) {
            final String url = saveImage(user.getCode(), image);
            user.setPhoto(url);
        }
    }

    /**
     * Save user's photo to server
     * @param userCode code of the user which photo needs to be saved
     * @param file file that needs to be saved
     * @return path of the saved image
     */
    private String saveImage(String userCode, MultipartFile file) {
        return upload.saveImage(userCode, file);
    }

    @Override
    public Class<User> getType() {
        return User.class;
    }

    @Override
    @Transactional
    public long createRole(final RoleType type) {
        LOG.debug("Creating user role with type: {}", type);
        final Role role = new Role(type);
        return userDao.createRole(role);
    }

    @Override
    @Transactional
    public Role findRoleByType(final RoleType type) {
        LOG.debug("Getting user role with type: {}", type);
        return userDao.findRoleByType(type);
    }

    @Override
    @Transactional
    public List<User> findAllByRole(final RoleType type) {
        LOG.debug("Getting user list with type: {}", type);
        return userDao.findAllByType(type);
    }

    @Override
    @Transactional
    public User loadUserByUsername(final String email) {
        LOG.debug("Loading user with email: {}", email);
        return userDao.findByEmail(email);
    }

    @Override
    @Transactional
    public void markUserForRemoval(String userCode) {
        final User user = findByCode(userCode);
        user.setStatus(UserStatus.FOR_REMOVAL);
        user.setRemovalDate(dateService.addDays(dateService.getCurrentDate(), USER_REMOVAL_DELAY));
        update(user);
    }

    @Override
    @Transactional
    public void activateUser(String userCode) {
        final User user = findByCode(userCode);
        user.setStatus(UserStatus.ACTIVE);
        super.update(user);
    }

    @Override
    @Transactional
    public void inactivateUser(String userCode) {
        final User user = findByCode(userCode);
        user.setStatus(UserStatus.INACTIVE);
        super.update(user);
    }

    @Override
    @Transactional
    public void restoreUser(String userCode) {
        final User user = findByCode(userCode);
        user.setStatus(UserStatus.INACTIVE);
        user.setRemovalDate(null);
        super.update(user);
    }

    @Override
    public Map<String, String> findAllUserNameAndCode(RoleType roleType) {
        final Map<String, String> results = new HashMap<>();
        final List<User> users = findAllByRole(roleType);
        for (final User user : users) {
            results.put(user.shortString(), user.getCode());
        }
        return results;
    }

    @Override
    public void registerUser(User user, MultipartFile img) {
        user.setStatus(UserStatus.ACTIVE);
        user.setAuthorities(Arrays.asList(findRoleByType(RoleType.ROLE_USER)));
        user.setRegisterDate(dateService.getCurrentDate());
        create(user, img, true);

        final SimpleMailMessage msg = mailMessageBuilder.build(user);
        mailService.sendMail(msg);
    }

    @Override
    @Transactional
    public boolean isEmailExist(String email) {
        return userDao.isEmailExist(email);
    }

    @Override
    protected ActivityEntity getActivityEntity() {
        return ActivityEntity.USER;
    }
}
