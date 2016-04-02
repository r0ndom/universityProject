package net.github.rtc.app.service.order;

import net.github.rtc.app.dao.generic.GenericDao;
import net.github.rtc.app.dao.order.UserCourseOrderDao;
import net.github.rtc.app.model.dto.SearchResults;
import net.github.rtc.app.model.dto.filter.AbstractSearchFilter;
import net.github.rtc.app.model.dto.filter.OrderSearchFilter;
import net.github.rtc.app.model.dto.user.ExpertOrderDto;
import net.github.rtc.app.model.entity.AbstractPersistenceObject;
import net.github.rtc.app.model.entity.course.Course;
import net.github.rtc.app.model.entity.course.CourseType;
import net.github.rtc.app.model.entity.order.UserRequestStatus;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.service.course.CourseService;
import net.github.rtc.app.service.generic.AbstractGenericServiceTest;
import net.github.rtc.app.model.entity.order.UserCourseOrder;
import net.github.rtc.app.service.date.DateService;
import net.github.rtc.app.service.generic.GenericService;
import net.github.rtc.app.service.message.MessageService;
import net.github.rtc.app.service.user.UserService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserCourseOrderServiceImplTest extends AbstractGenericServiceTest {

    @Mock
    private CourseService courseService;
    @Mock
    private UserService userService;
    @Mock
    UserCourseOrderDao userCourseOrderDao;
    @Mock
    private DateService dateService;
    @Mock
    private MessageService messageService;
    @Mock
    private UserCourseOrderService orderService;

    @InjectMocks
    private UserCourseOrderServiceImpl userCourseOrderService;

    @Test
    public void testSearchExpertOrders() throws Exception {

        final UserCourseOrder order = (UserCourseOrder) getTestEntity();

        final SearchResults<UserCourseOrder> courseList = new SearchResults<>();
        courseList.setResults(Arrays.asList(order));
        when(userCourseOrderDao.search(any(AbstractSearchFilter.class))).thenReturn(courseList);

        Course course = new Course();
        course.setCode(order.getCourseCode());
        course.setName("C_NAME");
        course.setCapacity(10);

        User user = new User();
        user.setCode(order.getUserCode());
        user.setName("NAME");
        user.setSurname("SNAME");

        int acceptedOrdersCount = 10;
        when(courseService.findByCode(order.getCourseCode())).thenReturn(course);
        when(userService.findByCode(order.getUserCode())).thenReturn(user);
        when(userCourseOrderDao.getAcceptedOrdersCourseCount(course.getCode())).thenReturn(acceptedOrdersCount);


        final SearchResults<ExpertOrderDto> expertOrders = userCourseOrderService.searchExpertOrders(new OrderSearchFilter());

        assertEquals(expertOrders.getResults().size(), 1);

        final ExpertOrderDto resultOrder = expertOrders.getResults().get(0);
        final ExpertOrderDto excepted = getOrderDto(order, course, user, acceptedOrdersCount);

        assertTrue(resultOrder.equals(excepted));


    }

    private ExpertOrderDto getOrderDto(UserCourseOrder order, Course course, User user, int acceptedOrders) {
        ExpertOrderDto orderDto = new ExpertOrderDto();

        orderDto.setOrderCode(order.getCode());
        orderDto.setStatus(order.getStatus());
        orderDto.setOrderDate(order.getRequestDate());

        orderDto.setCourseCode(course.getCode());
        orderDto.setCourseName(course.getName());
        orderDto.setCourseStartDate(course.getStartDate());
        orderDto.setCourseEndDate(course.getEndDate());
        orderDto.setCourseCapacity(course.getCapacity());

        orderDto.setUserCode(user.getCode());
        orderDto.setUserName(user.getSurnameName());
        orderDto.setUserPhoto(user.getPhoto());

        orderDto.setCourseAcceptedOrders(acceptedOrders);
        return orderDto;
    }

    @Test
    public void testGetAcceptedOrdersCount() throws Exception {
        String courseCode = "COURSE";

        userCourseOrderService.getAcceptedOrdersCount(courseCode);  //different name in dao|service

        verify(userCourseOrderDao).getAcceptedOrdersCourseCount(courseCode);
    }

    @Test
    public void testAcceptOrder() throws Exception {
        UserCourseOrder order = (UserCourseOrder) getTestEntity();
        when(userCourseOrderDao.findByCode(CODE)).thenReturn(order);
        when(dateService.getCurrentDate()).thenReturn(new Date());
        doAnswer(new Answer() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                return invocation.getArguments()[0];
            }
        }).when(userCourseOrderDao).update(any(UserCourseOrder.class));

        final UserCourseOrder resultOrder = userCourseOrderService.acceptOrder(CODE);

        assertEquals(resultOrder.getStatus(), UserRequestStatus.ACCEPTED);
        assertNotNull(resultOrder.getResponseDate());
        verify(messageService).createOrderResponseMessage(any(UserCourseOrder.class));
    }

    @Test
    public void testRejectOrder() throws Exception {
        UserCourseOrder order = (UserCourseOrder) getTestEntity();
        when(userCourseOrderDao.findByCode(CODE)).thenReturn(order);
        when(dateService.getCurrentDate()).thenReturn(new Date());
        doAnswer(new Answer() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                return invocation.getArguments()[0];
            }
        }).when(userCourseOrderDao).update(any(UserCourseOrder.class));
        String reason = "no no no no!!!";
        final UserCourseOrder resultOrder = userCourseOrderService.rejectOrder(CODE, reason);
        assertEquals(resultOrder.getStatus(), UserRequestStatus.REJECTED);
        assertEquals(resultOrder.getRejectReason(), reason);
        assertNotNull(resultOrder.getResponseDate());
        verify(messageService).createOrderResponseMessage(any(UserCourseOrder.class));
    }


    @Test
    public void testCreate() {
        super.testCreate();

        final User authorizedUser = authentication();

        doAnswer(new Answer() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                UserCourseOrder order = (UserCourseOrder) invocation.getArguments()[0];
                assertEquals(order.getCourseCode(), CODE);
                assertEquals(order.getPosition(), CourseType.BA);
                assertEquals(order.getUserCode(), authorizedUser.getCode());
                return null;
            }
        }).when(messageService).createOrderSendMessage(any(UserCourseOrder.class));

        when(userCourseOrderDao.create(any(UserCourseOrder.class))).thenReturn(new UserCourseOrder());

        userCourseOrderService.create(CODE, CourseType.BA);
    }


    private User authentication() {
        final User authorizedUser = new User();
        authorizedUser.setName("NAME");
        authorizedUser.setSurname("SURNAME");
        authorizedUser.setEmail("email@email.com");
        authorizedUser.setCode("USERCODE");
        Authentication auth = new UsernamePasswordAuthenticationToken(authorizedUser, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return authorizedUser;
    }

    @Test
    public void testFindByUserCodeAndCourseCode() throws Exception {
        String userCode = "USER";
        String courseCode = "COURSE";

        userCourseOrderService.findByUserCodeAndCourseCode(userCode, courseCode);

        verify(userCourseOrderDao).getUserCourseOrder(userCode, courseCode); //different name in dao|service
    }

    @Test
    public void testFindLastByUserCode() throws Exception {
        String userCode = "USER";

        userCourseOrderService.findLastByUserCode(userCode);

        verify(userCourseOrderDao).getLastUserCourseOrders(userCode);
    }


    @Override
    protected GenericService<AbstractPersistenceObject> getGenericService() {
        return (GenericService) userCourseOrderService;
    }

    @Override
    protected GenericDao<AbstractPersistenceObject> getGenericDao() {
        return (GenericDao) userCourseOrderDao;
    }

    @Override
    protected AbstractPersistenceObject getTestEntity() {
        final UserCourseOrder order = new UserCourseOrder();
        order.setCourseCode("COURSE_CODE");
        order.setUserCode("USER_CODE");
        order.setCode(CODE);
        order.setPosition(CourseType.BA);
        order.setRequestDate(new Date());
        return order;
    }

    @Override
    protected AbstractSearchFilter getSearchFilter() {
        return new OrderSearchFilter();
    }
}