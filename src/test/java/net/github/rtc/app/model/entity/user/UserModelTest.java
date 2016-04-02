package net.github.rtc.app.model.entity.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(BlockJUnit4ClassRunner.class)
public class UserModelTest {

    @Test
    public void userHasRole() {
        User user = new User();
        user.setAuthorities(Arrays.asList(new Role(RoleType.ROLE_ADMIN)));
        assertTrue(user.hasRole(RoleType.ROLE_ADMIN.name()));
        assertTrue(!user.hasRole(RoleType.ROLE_EXPERT.name()));
    }

    @Test
    public void toStringTest() {
        User user = new User();
        user.setName("X");
        user.setSurname("Y");
        String expect = "X" + " " + "Y";
        assertTrue(expect.equals(user.toString()));
    }

    @Test
    public void isActiveTest() {
        User user = new User();
        user.setStatus(UserStatus.ACTIVE);
        assertTrue(user.isActive());
    }

    @Test
    public void isForRemovalTest() {
        User user = new User();
        user.setStatus(UserStatus.FOR_REMOVAL);
        assertTrue(user.isForRemoval());
    }

    @Test
    public void toShortStringTest() {
        User user = new User();
        user.setName("X");
        user.setSurname("Y");
        user.setEmail("Z");
        String expect = "X" + " " + "Y" + " " + "Z";
        assertTrue(expect.equals(user.shortString()));
    }

    @Test
    public void roleToStringTest() {
        Role role = new Role();
        role.setName(RoleType.ROLE_ADMIN);
        assertEquals(RoleType.ROLE_ADMIN.name(), role.toString());
    }
}
