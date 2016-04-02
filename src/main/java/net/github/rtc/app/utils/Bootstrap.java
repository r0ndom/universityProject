package net.github.rtc.app.utils;

import net.github.rtc.app.model.entity.user.EnglishLevel;
import net.github.rtc.app.model.entity.user.RoleType;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.model.entity.user.UserStatus;
import net.github.rtc.app.service.date.DateService;
import net.github.rtc.app.service.user.UserService;
import org.jasypt.hibernate4.encryptor.HibernatePBEStringEncryptor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Profile("dev")
@Component
@DependsOn("allowEncryptionWithoutJCE")
public class Bootstrap implements InitializingBean {

    private static final String ADMIN = "admin";
    private static final String ADMIN_EMAIL = "admin@email.com";
    @Autowired
    private HibernatePBEStringEncryptor hibernateStringEncryptor;
    @Autowired
    private UserService userService;
    @Autowired
    private DateService dateService;

    public void loadTestUsers() {
        if (userService.loadUserByUsername(ADMIN_EMAIL) == null) {
            if (userService.findRoleByType(RoleType.ROLE_ADMIN) == null) {
                userService.createRole(RoleType.ROLE_ADMIN);
            }
            if (userService.findRoleByType(RoleType.ROLE_USER) == null) {
                userService.createRole(RoleType.ROLE_USER);
            }
            if (userService.findRoleByType(RoleType.ROLE_EXPERT) == null) {
                userService.createRole(RoleType.ROLE_EXPERT);
            }

            final User admin = new User("TestName", "TestMiddlename", "TestSurname", ADMIN_EMAIL, ADMIN);
            admin.setAuthorities(Arrays.asList(userService.findRoleByType(RoleType.ROLE_ADMIN)));
            admin.setRegisterDate(dateService.getCurrentDate());
            admin.setGender("Male");
            admin.setPhone("123456");
            admin.setNote("note");
            admin.setBirthDate(dateService.getCurrentDate());
            admin.setStatus(UserStatus.ACTIVE);
            admin.setEnglish(EnglishLevel.INTERMEDIATE);
            admin.setCity("Dp");
            admin.setUniversity("");
            admin.setFaculty("");
            admin.setSpeciality("");
            userService.create(admin);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loadTestUsers();
    }
}
