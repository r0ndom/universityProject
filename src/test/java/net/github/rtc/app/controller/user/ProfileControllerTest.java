package net.github.rtc.app.controller.user;

import net.github.rtc.app.controller.common.ErrorControllerTest;
import net.github.rtc.app.model.entity.user.Role;
import net.github.rtc.app.model.entity.user.RoleType;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.service.course.CourseService;
import net.github.rtc.app.service.order.UserCourseOrderService;
import net.github.rtc.app.service.user.UserService;
import net.github.rtc.app.service.date.DateService;
import net.github.rtc.util.converter.ValidationContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class ProfileControllerTest {

    private static final String EMAIL = "vasya@mail.ru";

    private static final String NAME = "vasya";

    private static final String SURNAME = "pupkin";

    @InjectMocks
    private ProfileController controller;

    @Mock
    private UserService userService;
    @Mock
    private CourseService courseService;
    @Mock
    private UserCourseOrderService userCourseOrderService;
    @Mock
    private ValidationContext validationContext;
    @Mock
    private DateService dateService;
    @Mock
    private AuthenticationManager authenticationManager;

    private User user;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.user = new User();
        user.setEmail(EMAIL);
        user.setName(NAME);
        user.setSurname(SURNAME);
        user.setAuthorities(Arrays.asList(new Role(RoleType.ROLE_USER)));
        Authentication auth = new UsernamePasswordAuthenticationToken(user,null);
        SecurityContextHolder.getContext().setAuthentication(auth);
        when(userService.loadUserByUsername(EMAIL)).thenReturn(user);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testUserView() throws Exception {
        mockMvc.perform(get("/user/profile"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void testEditUser() throws Exception {
        UsernamePasswordAuthenticationToken principal =
                new UsernamePasswordAuthenticationToken("user1", "1111");
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                new ErrorControllerTest.MockSecurityContext(principal));
        User user = new User();
        user.setAuthorities(Arrays.asList(new Role(RoleType.ROLE_ADMIN)));
        when(userService.loadUserByUsername("user1")).thenReturn(user);

        when(validationContext.get(User.class)).thenReturn("validationContext");
        mockMvc.perform(get("/user/profile/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("validationRules"));
    }


//    todo
//    @Test
//    public void testUpdateUser() throws Exception {
//        mockMvc.perform(post("/user/update").sessionAttr("user", user))
//                .andExpect(status().isFound())
//                .andExpect(redirectedUrl("/user/view" + CURRENT_USERNAME));
//    }

}

