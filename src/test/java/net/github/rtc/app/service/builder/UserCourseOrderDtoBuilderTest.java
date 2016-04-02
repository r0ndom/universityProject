package net.github.rtc.app.service.builder;

import net.github.rtc.app.model.dto.user.UserCourseOrderDto;
import net.github.rtc.app.model.entity.course.Course;
import net.github.rtc.app.model.entity.order.UserCourseOrder;
import net.github.rtc.app.model.entity.order.UserRequestStatus;
import net.github.rtc.app.model.entity.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.Mock;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class UserCourseOrderDtoBuilderTest {
    @Mock
    private UserCourseOrder testOrder;
    @Mock
    private Course testCourse;

    @Before
    public void prepareData() {
        Set experts = new HashSet();
        experts.add(new User("name", "surname", "middle", "email@email.com","pass"));
        testCourse = new Course();
        testCourse.setCapacity(10);
        testCourse.setName("testCourse");
        testCourse.setExperts(experts);
        testCourse.setStartDate(new Date());
        testCourse.setEndDate(new Date());
        testCourse.setCode("codeCourse");

        testOrder = new UserCourseOrder();
        testOrder.setCode("orderCode");
        testOrder.setStatus(UserRequestStatus.ACCEPTED);
        testOrder.setRequestDate(new Date());
    }

    @Test
    public void testBuildCourseFields() {
        UserCourseOrderDtoBuilder dtoBuilder = new UserCourseOrderDtoBuilder(testOrder, testCourse);
        UserCourseOrderDto dto = dtoBuilder.build();
        assertEquals(dto.getCode(), testCourse.getCode());
        assertEquals(dto.getEndDate(), testCourse.getEndDate());
        assertEquals(dto.getName(), testCourse.getName());
        assertEquals(dto.getStartDate(), testCourse.getStartDate());
        assertEquals(dto.getExperts(), testCourse.getExperts());
        assertEquals(dto.getStatus(), testOrder.getStatus());
    }
}
