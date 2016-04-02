package net.github.rtc.app.controller.common;


import net.github.rtc.app.model.entity.user.Role;
import net.github.rtc.app.model.entity.user.RoleType;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:mvc-test.xml")
public class ErrorControllerTest  {

    private static final String JAVAX = "javax";
    private static final String DOT_SERVLET = ".servlet";

    public static class MockSecurityContext implements SecurityContext {

        private static final long serialVersionUID = -1386535243513362694L;

        private Authentication authentication;

        public MockSecurityContext(Authentication authentication) {
            this.authentication = authentication;
        }

        @Override
        public Authentication getAuthentication() {
            return this.authentication;
        }

        @Override
        public void setAuthentication(Authentication authentication) {
            this.authentication = authentication;
        }
    }

    @Mock
    private UserService userService;
    @InjectMocks
    private ErrorController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testErrorPage500() throws Exception {
        UsernamePasswordAuthenticationToken principal =
                new UsernamePasswordAuthenticationToken("user1", "1111");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                new MockSecurityContext(principal));
        User user = new User();
        user.setAuthorities(Arrays.asList(new Role(RoleType.ROLE_ADMIN)));
        when(userService.loadUserByUsername("user1")).thenReturn(user);
        mockMvc.perform(get("/error500").requestAttr(JAVAX + DOT_SERVLET
                + ".error.status_code", new Integer(500)).session(session))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errorTitle", "errorMessage", "errorCause"));
    }

    @Test
    public void testErrorPage404() throws Exception {
        UsernamePasswordAuthenticationToken principal =
                new UsernamePasswordAuthenticationToken("user1", "1111");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                new MockSecurityContext(principal));
        User user = new User();
        user.setAuthorities(Arrays.asList(new Role(RoleType.ROLE_ADMIN)));
        when(userService.loadUserByUsername("user1")).thenReturn(user);
        mockMvc.perform(get("/error404").requestAttr(JAVAX + DOT_SERVLET
                + ".error.status_code", new Integer(404)).session(session))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errorTitle", "errorMessage", "errorCause"));

    }

}
