package net.github.rtc.app.controller.admin;

import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.service.user.UserService;
import net.github.rtc.util.converter.ValidationContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Berdniky on 18.11.2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:mvc-test.xml")
@WebAppConfiguration
public class UserControllerTest {

    private static final String ROOT = "portal/admin";
    private static final String PATH_PAGE_VIEW_ALL_USERS = "/page/viewAllusers";
    private static final String PATH_ADMIN_USER = "admin/user";
    private static final String AUTHORITIES = "authorities";
    private static final String USER_FILTER = "userFilter";
    private static final String PATH_PAGE_USER_PAGE = "/page/userPage";
    private static final String ADMIN_SEARCH = "/admin/search";

    private static final String VALIDATION_RULES = "validationRules";
    private static final String USER = "user";
    private static final String ANY_USER_CODE = "anyCode";
    private static final String ANY_STRING = "anyString";
    private static final String USER_CODE = "userCode";

    @InjectMocks
    private UserController userController;
    private MockMvc mockMvc;

    @Mock
    private List<User> userList;
    @Mock
    private UserService userService;
    @Mock
    private ValidationContext validationContext;
    @Mock
    private User user;

    @Before
    public void prepareUsersList(){
        MockitoAnnotations.initMocks(this);
        Authentication auth = new UsernamePasswordAuthenticationToken(new User(), null);
        SecurityContextHolder.getContext().setAuthentication(auth);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void editPage () throws Exception{
        when(userService.findByCode(ANY_USER_CODE)).thenReturn(user);
        when(validationContext.get(User.class)).thenReturn(ANY_STRING);
        mockMvc.perform(get("/admin/user/edit/{code}", ANY_USER_CODE))
                .andExpect(status().isOk())
                .andExpect(view().name(ROOT + "/user/userUpdate"))
                .andExpect(model().attributeExists(VALIDATION_RULES))
                .andExpect(model().attributeExists(USER));
        verify(userService, times(1)).findByCode(ANY_USER_CODE);
        verify(validationContext, times(1)).get(User.class);
        /*verifyNoMoreInteractions(userService);*/
    }

// todo
//    @Test
//    public void userPage () throws Exception{
//        when(userService.findByCode(ANY_USER_CODE)).thenReturn(user);
//        mockMvc.perform(get("/admin/user/userDetails/{code}", ANY_USER_CODE))
//                .andExpect(status().isOk())
//                .andExpect(view().name(ROOT + "/user/userDetails"))
//                .andExpect(model().attributeExists(USER));
//        verify(userService, times(1)).findByCode(ANY_USER_CODE);
//        /*verifyNoMoreInteractions(userService);*/
//    }
    @Test
    public void remove () throws Exception {
        mockMvc.perform(get("/admin/user/remove").param("userCode",ANY_USER_CODE))
                .andExpect(status().isFound())
                /*.andExpect(redirectedUrl(ADMIN_SEARCH))*/;
        verify(userService, times(1)).markUserForRemoval(ANY_USER_CODE);
    }

    /*@Test
    public void setStatusActiveAndRestore () throws Exception {
        mockMvc.perform(get("/admin/user/restore/{userCode}", ANY_USER_CODE))
                .andExpect(status().isOk());
    }*/
}
