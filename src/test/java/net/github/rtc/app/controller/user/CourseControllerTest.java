package net.github.rtc.app.controller.user;

import net.github.rtc.app.model.dto.user.UserCourseDto;
import net.github.rtc.app.model.entity.course.Course;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.model.entity.order.UserCourseOrder;
import net.github.rtc.app.service.course.CourseService;
import net.github.rtc.app.service.date.DateService;
import net.github.rtc.app.service.order.UserCourseOrderService;
import net.github.rtc.app.service.user.UserService;
import net.github.rtc.app.model.dto.filter.CourseSearchFilter;
import net.github.rtc.app.model.dto.SearchResults;
import net.github.rtc.util.converter.ValidationContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = "classpath:mvc-test.xml")
public class CourseControllerTest {

    private static final String EMAIL = "vasya@mail.ru";

    private static final String NAME = "vasya";

    private static final String SURNAME = "pupkin";

    private static final String COURSE_CODE = "X";

    @InjectMocks
    private CourseController controller;

    @Mock
    private CourseSearchFilter courseSearchFilter;
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
        Authentication auth = new UsernamePasswordAuthenticationToken(user,null);
        SecurityContextHolder.getContext().setAuthentication(auth);
        when(userService.loadUserByUsername(EMAIL)).thenReturn(user);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testUserCoursesTable() throws Exception {
        SearchResults<UserCourseDto> searchResults = new SearchResults<>();
        searchResults.setResults(new ArrayList<UserCourseDto>());
        searchResults.getPageModel().setPage(10);
        searchResults.getPageModel().setPerPage(10);
        searchResults.getPageModel().setTotalResults(10);
        when(courseService.searchUserCourses(isA(CourseSearchFilter.class))).thenReturn(searchResults);
        mockMvc.perform(post("/user/courses/courseTable"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("courses"));
    }

    @Test
    public void testUserCourses() throws Exception {
        mockMvc.perform(get("/user/courses"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("portal/user/course/courses"));
    }

    @Test
    public void testCourseDetails() throws Exception {
        when(courseService.getUserCourseDtoByCode(COURSE_CODE)).thenReturn(new UserCourseDto());
        mockMvc.perform(get("/user/courses/courseDetails/{courseCode}", COURSE_CODE))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("course"))
                .andExpect(forwardedUrl("portal/user/course/userCourseDetails"));
    }

    @Test
    public void testSendCourseOrder() throws Exception {
        mockMvc.perform(post("/user/courses/sendOrder").sessionAttr("order", new UserCourseOrder()))
                .andExpect(status().isFound());
    }

    @Test
    public void testCoursePosition() throws Exception {
        when(courseService.findByCode(COURSE_CODE)).thenReturn(new Course());
        mockMvc.perform(get("/user/courses/position/{courseCode}", COURSE_CODE))
                .andExpect(status().isOk());
    }
}
