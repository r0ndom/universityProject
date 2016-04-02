/*package net.github.rtc.app.dao.impl;

import net.github.rtc.app.dao.*;
import net.github.rtc.app.dao.generic.GenericDao;
import net.github.rtc.app.dao.user.UserDao;
import net.github.rtc.app.model.entity.user.Role;
import net.github.rtc.app.model.entity.user.RoleType;
import net.github.rtc.app.model.entity.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.junit.Ignore;
import javax.validation.constraints.Pattern;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;



@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoTest extends AbstractGenericDaoTest<User> {
    @Autowired
    private UserDao userDao;


    @Override
    protected GenericDao<User> getGenericDao() {
        return userDao;
    }

    @Override
    protected ModelBuilder getModelBuilder() {
        return daoTestContext.getModelBuilder(User.class);
    }

    @Override
    protected EqualityChecker getEqualityChecker() {
        return daoTestContext.getEqualityChecker(User.class);
    }

    @Test
    @Transactional
    @Pattern(regexp = "", flags = {Pattern.Flag.CASE_INSENSITIVE})
    public void testFindByEmail(){
        final String email1 = "email1@email.com";
        final String email2 = "email2@email.com";
        User user1 = (User) getModelBuilder().build();
        user1.setEmail(email1);
        User user2 = (User) getModelBuilder().build();
        user2.setEmail(email2);
        getGenericDao().create(user1);
        getGenericDao().create(user2);
        User foundUser  = userDao.findByEmail(email1);
        getEqualityChecker().check(foundUser, user1);
    }

    @Test
    @Transactional
    public void testGetRoleByType(){
        initRole();
        Role foundRole = userDao.getRoleByType(RoleType.ROLE_ADMIN);
        assertEquals(foundRole.getName(), RoleType.ROLE_ADMIN);
    }

    @Test
    @Transactional
    public void testCreateRole(){
        assertNotNull(userDao.createRole(RoleType.ROLE_EXPERT));
    }

    @Ignore //getUsersByType resultList always empty. CreateAlias is not work
    @Test
    @Transactional
    public void testGetUsersByType(){
        initRole();

        User user1 = (User) getModelBuilder().build();
        user1.setAuthorities(Arrays.asList(userDao.getRoleByType(RoleType.ROLE_ADMIN)));
        User user2 = (User) getModelBuilder().build();
        user2.setAuthorities(Arrays.asList(userDao.getRoleByType(RoleType.ROLE_USER)));
        User user3 = (User) getModelBuilder().build();
        user3.setAuthorities(Arrays.asList(userDao.getRoleByType(RoleType.ROLE_USER)));
        getGenericDao().create(user1);
        getGenericDao().create(user2);
        getGenericDao().create(user3);

        final User byCode = userDao.findByCode(user1.getCode());
        final User byCode1 = userDao.findByCode(user2.getCode());
        final User byCode2 = userDao.findByCode(user3.getCode());

        List<User> users = userDao.getUsersByType(RoleType.ROLE_ADMIN);
        assertEquals(1, users.size());
        for(final User u : users) {
            for(final GrantedAuthority r : u.getAuthorities()){
                assertEquals("ROLE_ADMIN", r.getAuthority());
            }
        }
    }

    @Test
    @Transactional
    public void testDeletingUser(){
        User user1 = (User) getModelBuilder().build();
        Date removal = new Date();
        User user2 = (User) getModelBuilder().build();
        user2.setRemovalDate(removal);
        User user3 = (User) getModelBuilder().build();
        user3.setRemovalDate(removal);
        getGenericDao().create(user1);
        getGenericDao().create(user2);
        getGenericDao().create(user3);
        userDao.deletingUser();
        assertEquals(1, getGenericDao().findAll().size());
    }


    private void initRole() {
        userDao.createRole(RoleType.ROLE_ADMIN);
        userDao.createRole(RoleType.ROLE_EXPERT);
        userDao.createRole(RoleType.ROLE_USER);
    }
}
*/