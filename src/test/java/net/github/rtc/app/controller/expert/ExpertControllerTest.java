package net.github.rtc.app.controller.expert;

import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.model.entity.order.UserCourseOrder;
import net.github.rtc.app.service.course.CourseService;
import net.github.rtc.app.service.order.UserCourseOrderService;
import net.github.rtc.app.service.user.UserService;
import net.github.rtc.app.service.date.DateService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:mvc-test.xml")
public class ExpertControllerTest {

    private static final String EMAIL = "vasya@mail.ru";

    private static final String ORDER_CODE = "X";

    private static final String CURRENT_USER = "?currentUserName=vasya%40mail.ru";

    private static final String NAME = "vasya";

    private static final String SURNAME = "pupkin";

    @InjectMocks
    private OrderController controller;

    @Mock
    private CourseService courseService;
    @Mock
    private UserCourseOrderService userCourseOrderService;
    @Mock
    private UserService userService;
    @Mock
    private DateService dateService;

    private User user;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.user = new User();
        user.setEmail(EMAIL);
        user.setName(NAME);
        user.setSurname(SURNAME);
        Authentication auth = new UsernamePasswordAuthenticationToken(user,null);
        SecurityContextHolder.getContext().setAuthentication(auth);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    public void testExpertCourses() throws Exception {
        //when(userCourseOrderService.getOrderByExpertCode(user.getCode())).thenReturn(new ArrayList<ExpertOrderDto>());
        mockMvc.perform(get("/expert/order"))
                .andExpect(status().isOk());
    }


    @Test
    public void testAcceptRequest() throws Exception {
        when(userCourseOrderService.findByCode(ORDER_CODE)).thenReturn(new UserCourseOrder());
        mockMvc.perform(get("/expert/order/accept/{orderId}", ORDER_CODE))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeclineRequest() throws Exception {
        when(userCourseOrderService.findByCode(ORDER_CODE)).thenReturn(new UserCourseOrder());
        mockMvc.perform(get("/expert/order/decline/{orderId}", ORDER_CODE).param("reason","reason"))
                .andExpect(status().isOk());
    }

}
