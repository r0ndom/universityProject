package net.github.rtc.app.model.dto;

import net.github.rtc.app.model.dto.user.ExpertOrderDto;
import net.github.rtc.app.service.builder.ExpertOrderDtoBuilder;
import net.github.rtc.app.model.entity.course.Course;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.model.entity.order.UserCourseOrder;
import net.github.rtc.app.model.entity.order.UserRequestStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;

public class ExpertOrderDtoBuilderTest {

    private UserCourseOrder testOrder;
    private User testUser;
    private Course testCourse;

    @Before
    public void prepareData() {
        testUser = new User();
        testUser.setName("testName");
        testUser.setSurname("testSurname");
        testUser.setPhoto("testUrl");
        testUser.setCode("userCode");

        testCourse = new Course();
        testCourse.setCapacity(10);
        testCourse.setName("testCourse");
        testCourse.setStartDate(new Date());
        testCourse.setEndDate(new Date());
        testCourse.setCode("codeCourse");

        testOrder = new UserCourseOrder();
        testOrder.setCode("orderCode");
        testOrder.setStatus(UserRequestStatus.ACCEPTED);
        testOrder.setRequestDate(new Date());
    }

    @Test
    public void testDtoBuilder() {
        ExpertOrderDtoBuilder dtoBuilder = new ExpertOrderDtoBuilder();
        ExpertOrderDto dto = dtoBuilder.buildCourseFields(testCourse).buildUserFields(testUser).
                buildOrderFields(testOrder).buildAcceptedOrders(0).get();
        assertEquals(dto.getCourseAcceptedOrders(), 0);

        assertEquals(dto.getCourseCapacity(), (int)testCourse.getCapacity());
        assertEquals(dto.getCourseCode(), testCourse.getCode());
        assertEquals(dto.getCourseEndDate(), testCourse.getEndDate());
        assertEquals(dto.getCourseStartDate(), testCourse.getStartDate());
        assertEquals(dto.getCourseName(), testCourse.getName());

        assertEquals(dto.getUserCode(), testUser.getCode());
        assertEquals(dto.getUserName(), testUser.getSurnameName());
        assertEquals(dto.getUserPhoto(), testUser.getPhoto());

        assertEquals(dto.getOrderCode(), testOrder.getCode());
        assertEquals(dto.getOrderDate(), testOrder.getRequestDate());
    }

    @Test(expected=NullPointerException.class)
    public void testDtoBuilderException() {
        ExpertOrderDtoBuilder dtoBuilder = new ExpertOrderDtoBuilder();
        ExpertOrderDto dto = dtoBuilder.buildCourseFields(null).buildUserFields(testUser).
                buildOrderFields(testOrder).buildAcceptedOrders(0).get();

    }
}
