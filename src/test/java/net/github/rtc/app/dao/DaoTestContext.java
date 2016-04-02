package net.github.rtc.app.dao;

import net.github.rtc.app.model.entity.AbstractPersistenceObject;
import net.github.rtc.app.model.entity.course.Course;
import net.github.rtc.app.model.entity.course.CourseType;
import net.github.rtc.app.model.entity.user.EnglishLevel;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.model.entity.order.UserCourseOrder;
import net.github.rtc.app.model.entity.order.UserRequestStatus;
import net.github.rtc.app.service.date.DateService;
import net.github.rtc.app.service.generic.CodeGenerationService;
import net.github.rtc.app.service.generic.CodeGenerationServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static junit.framework.Assert.assertEquals;

@Component
public class DaoTestContext implements InitializingBean {
    private Map<Class, ModelBuilder<? extends AbstractPersistenceObject>> modelBuilder;
    private Map<Class, EqualityChecker> equalityChecker;
    protected final CodeGenerationService codeGenerationService = new CodeGenerationServiceImpl();


    public ModelBuilder getModelBuilder(Class aClass) {
        return modelBuilder.get(aClass);
    }

    public EqualityChecker getEqualityChecker(Class aClass) {
        return equalityChecker.get(aClass);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initEqualityChecker();
        initModelBuilder();
    }

    public DaoTestContext() {
        initModelBuilder();
        initEqualityChecker();
    }

    private void initModelBuilder() {
        modelBuilder = new HashMap<>();
        modelBuilder.put(Course.class,  new ModelBuilder<Course>() {
            @Override
            public Course build() {
                final Course course = new Course();
                course.setName("Test Course");
                course.setCapacity(1);
                course.setTypes(new HashSet<>(Arrays.asList(CourseType.BA, CourseType.QA)));
                course.setEndDate(getCurrentDate());
                course.setStartDate(getCurrentDate());
                course.setCode(codeGenerationService.generate());
                return course;
            }
        });

        modelBuilder.put(User.class,  new ModelBuilder<User>() {
            @Override
            public User build() {
                final User user = new User();
                user.setName("NAME");
                user.setSurname("SURNAME");
                user.setEmail("testMail@mail.com");
                user.setNote("note");
                user.setCode(codeGenerationService.generate());
                user.setBirthDate(getCurrentDate());
                user.setPhone("321312");
                user.setEnglish(EnglishLevel.BASIC);
                user.setPassword("1111");
                user.setRegisterDate(getCurrentDate());
                return user;
            }
        });

        modelBuilder.put(UserCourseOrder.class,  new ModelBuilder<UserCourseOrder>() {
            @Override
            public UserCourseOrder build() {
                final UserCourseOrder userCourseOrder = new UserCourseOrder();
                userCourseOrder.setCode(codeGenerationService.generate());
                userCourseOrder.setCourseCode(codeGenerationService.generate());
                userCourseOrder.setPosition(CourseType.BA);
                userCourseOrder.setRejectReason("bla bla");
                userCourseOrder.setStatus(UserRequestStatus.PENDING);
                userCourseOrder.setUserCode(codeGenerationService.generate());
                return userCourseOrder;
            }
        });
    }

    private void initEqualityChecker() {
        equalityChecker = new HashMap<>();
        equalityChecker.put(Course.class,  new EqualityChecker() {
            @Override
            public void check(final AbstractPersistenceObject sample, final AbstractPersistenceObject another) {
                final Course courseSample = (Course)sample;
                final Course courseAnother = (Course)another;
                assertEquals(courseSample.getName(), courseAnother.getName());
                assertEquals(courseSample.getCapacity(), courseAnother.getCapacity());
                assertEquals(courseSample.getId(), courseAnother.getId());
                assertEquals(courseSample.getEndDate(), courseAnother.getEndDate());
                assertEquals(courseSample.getStartDate(), courseAnother.getStartDate());
                assertEquals(courseSample.getCode(), courseAnother.getCode());
                assertEquals(courseSample.getTypes(), courseAnother.getTypes());
            }});
        equalityChecker.put(User.class,  new EqualityChecker() {
            @Override
            public void check(final AbstractPersistenceObject sample, final AbstractPersistenceObject another) {
                final User userSample = (User)sample;
                final User userAnother = (User)another;
                assertEquals(userSample.getEmail(), userAnother.getEmail());
                assertEquals(userSample.getPassword(), userAnother.getPassword());
            }});
        equalityChecker.put(UserCourseOrder.class,  new EqualityChecker() {
            @Override
            public void check(final AbstractPersistenceObject sample, final AbstractPersistenceObject another) {
                final UserCourseOrder courseOrderSample = (UserCourseOrder)sample;
                final UserCourseOrder courseOrderAnother = (UserCourseOrder)another;
                assertEquals(courseOrderSample.getCode(), courseOrderAnother.getCode());
                assertEquals(courseOrderSample.getCourseCode(), courseOrderAnother.getCourseCode());
                assertEquals(courseOrderSample.getPosition(), courseOrderAnother.getPosition());
                assertEquals(courseOrderSample.getRejectReason(), courseOrderAnother.getRejectReason());
                assertEquals(courseOrderSample.getStatus(), courseOrderAnother.getStatus());
                assertEquals(courseOrderSample.getUserCode(), courseOrderAnother.getUserCode());
            }});
    }

    private Date getCurrentDate() {
        return new Date(0);
    }

}
