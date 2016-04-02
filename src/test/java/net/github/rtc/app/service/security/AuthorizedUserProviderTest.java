package net.github.rtc.app.service.security;

import net.github.rtc.app.model.entity.user.Role;
import net.github.rtc.app.model.entity.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class AuthorizedUserProviderTest {

    final User authorizedUser = new User();

    final private static String ANONYMOUS = "anonymousUser";

    @Before
    public void prepareUsersList(){
        authorizedUser.setName("NAME");
        authorizedUser.setSurname("SURNAME");
        authorizedUser.setEmail("email@email.com");
        authorizedUser.setCode("CODE");
        Authentication auth = new UsernamePasswordAuthenticationToken(authorizedUser, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    public void getAuthorizedUserTest() {
        User user = AuthorizedUserProvider.getAuthorizedUser();
        assertEquals(authorizedUser.getName(), user.getName());
        assertEquals(authorizedUser.getSurname(),user.getSurname());
        assertEquals(authorizedUser.getEmail(),user.getEmail());
        assertEquals(authorizedUser.getCode(),user.getCode());

    }

    @Test
    public void getAuthorizedUserNameTest() {
        assertEquals(authorizedUser.getUsername(), AuthorizedUserProvider.getAuthorizedUserName());
    }

    @Test
    public void getAnonTest() {
        SecurityContextHolder.getContext().setAuthentication(
                new AnonymousAuthenticationToken("xxx",new User(), Arrays.asList(new Role())));
        User user = AuthorizedUserProvider.getAuthorizedUser();
        assertEquals(ANONYMOUS, user.getName());
        assertEquals(ANONYMOUS,user.getSurname());
        assertEquals(ANONYMOUS + "@" + ANONYMOUS + "." + ANONYMOUS,user.getEmail());
        assertNull(user.getCode());

        prepareUsersList();
    }

}
