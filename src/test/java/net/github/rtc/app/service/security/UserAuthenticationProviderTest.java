package net.github.rtc.app.service.security;

import net.github.rtc.app.dao.DaoTestContext;
import net.github.rtc.app.model.entity.user.Role;
import net.github.rtc.app.model.entity.user.RoleType;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.model.entity.user.UserStatus;
import net.github.rtc.app.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;


import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class UserAuthenticationProviderTest {
    @Mock
    private UserService userService;
    @InjectMocks
    UserAuthenticationProvider userAuthenticationProvider = new UserAuthenticationProvider();
    protected DaoTestContext daoTestContext = new DaoTestContext();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test(expected = BadCredentialsException.class)
    public void testAuthenticateEmptyEmail() throws Exception {
        final Authentication wrongAuthentication = new TestingAuthenticationToken("", "password");
        userAuthenticationProvider.authenticate(wrongAuthentication);
    }

    @Test(expected = BadCredentialsException.class)
    public void testAuthenticateEmptyPassword() throws Exception {
        final Authentication wrongAuthentication = new TestingAuthenticationToken("mail", "");
        userAuthenticationProvider.authenticate(wrongAuthentication);
    }

    @Test(expected = BadCredentialsException.class)
    public void testAuthenticateUserNotFound() throws Exception {
        when(userService.loadUserByUsername(anyString())).thenReturn(null);

        final Authentication wrongAuthentication = new TestingAuthenticationToken("mail", "password");
        userAuthenticationProvider.authenticate(wrongAuthentication);
    }

    @Test(expected = BadCredentialsException.class)
    public void testAuthenticateUserNotActive() throws Exception {
        final User correctUser = getSampleUser();
        correctUser.setStatus(UserStatus.INACTIVE);
        when(userService.loadUserByUsername(correctUser.getEmail())).thenReturn(correctUser);

        final Authentication correctAuthentication = new TestingAuthenticationToken(correctUser.getEmail(),
                "pass");
        final Authentication authenticate = userAuthenticationProvider.authenticate(correctAuthentication);
    }

    @Test(expected = BadCredentialsException.class)
    public void testAuthenticateWrongPassword() throws Exception {
        final User correctUser = getSampleUser();
        correctUser.setPassword("password");
        when(userService.loadUserByUsername(correctUser.getEmail())).thenReturn(correctUser);

        final Authentication correctAuthentication = new TestingAuthenticationToken(correctUser.getEmail(),
                correctUser.getPassword());
        final Authentication authenticate = userAuthenticationProvider.authenticate(correctAuthentication);
    }


    @Test
    public void testAuthenticateTrue() throws Exception {
        final User correctUser = getSampleUser();
        correctUser.setEmail("true@email.com");
        correctUser.setName("TRUE_NAME");
        correctUser.setPassword("password");
        correctUser.setAuthorities(Arrays.asList(new Role(RoleType.ROLE_ADMIN)));
        correctUser.setStatus(UserStatus.ACTIVE);
        when(userService.loadUserByUsername(correctUser.getEmail())).thenReturn(correctUser);


        final Authentication correctAuthentication = new TestingAuthenticationToken(correctUser.getEmail(),
                correctUser.getPassword());


        final Authentication authenticate = userAuthenticationProvider.authenticate(correctAuthentication);
        assertUserEquals((User) authenticate.getPrincipal(), correctUser);
        assertEquals(authenticate.getAuthorities().size(), correctUser.getAuthorities().size());
        for (GrantedAuthority currentRole : authenticate.getAuthorities()) {
            assertTrue(correctUser.getAuthorities().contains(currentRole));
        }

    }

    @Test
    public void testSupports() throws Exception {
        final boolean isSupports = userAuthenticationProvider.supports(Object.class);
        assertTrue(isSupports);
    }


    private void assertUserEquals(User user1, User user2) {
        daoTestContext.getEqualityChecker(User.class).check(user1, user2);
    }

    private User getSampleUser() {
        return (User) (daoTestContext.getModelBuilder(User.class).build());
    }


}