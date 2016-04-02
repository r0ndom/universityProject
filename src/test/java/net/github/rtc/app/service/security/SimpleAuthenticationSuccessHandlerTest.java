package net.github.rtc.app.service.security;

import net.github.rtc.app.model.entity.user.Role;
import net.github.rtc.app.model.entity.user.RoleType;
import net.github.rtc.app.model.entity.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.WebAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(BlockJUnit4ClassRunner.class)
public class SimpleAuthenticationSuccessHandlerTest {


    @Test
    public void testAdminSuccessHandle() throws IOException {
        testHandle("/admin", getAuth(RoleType.ROLE_ADMIN));
    }

    @Test
    public void testUserSuccessHandle() throws IOException {
        testHandle("/user/profile/", getAuth(RoleType.ROLE_USER));
    }

    @Test
    public void testUserExpertHandle() throws IOException {
        testHandle("/expert/profile/", getAuth(RoleType.ROLE_EXPERT));
    }

    @Test
    public void testEmptyHandle() throws IOException {
        testHandle("/", getEmptyAuth());
    }

    private void testHandle(String targetUrl, Authentication authentication) throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        SimpleAuthenticationSuccessHandler successHandler = new SimpleAuthenticationSuccessHandler();
        request.setRequestURI("/");
        HttpSession session = new MockHttpSession();
        session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, new Exception());
        successHandler.onAuthenticationSuccess(request, response, authentication);
        assertNull(request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION));
        assertEquals(targetUrl, response.getRedirectedUrl());
    }

    @Test
    public void testSessionNullHandle() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        SimpleAuthenticationSuccessHandler successHandler = new SimpleAuthenticationSuccessHandler();
        request.setRequestURI("/");
        request.setSession(null);
        successHandler.onAuthenticationSuccess(request, response, getAuth(RoleType.ROLE_USER));
        assertNull(request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION));
        assertEquals("/user/profile/", response.getRedirectedUrl());
    }

    @Test
    public void testCommittedResponseHandle() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setCommitted(true);
        SimpleAuthenticationSuccessHandler successHandler = new SimpleAuthenticationSuccessHandler();
        request.setRequestURI("/");
        successHandler.handle(request, response, getAuth(RoleType.ROLE_EXPERT));
        assertNull(response.getRedirectedUrl());
    }


    private Authentication getAuth(RoleType roleType) {
        Role adminRole = new Role(roleType);

        User authorizedUser = new User();
        authorizedUser.setEmail("mail");
        authorizedUser.setAuthorities(Arrays.asList(adminRole));

        return new UsernamePasswordAuthenticationToken(authorizedUser, null);
    }


    private Authentication getEmptyAuth() {
        User authorizedUser = new User();
        authorizedUser.setEmail("mail");
        authorizedUser.setAuthorities(new ArrayList<Role>());

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        return new UsernamePasswordAuthenticationToken(authorizedUser, null, grantedAuthorityList);
    }
}
