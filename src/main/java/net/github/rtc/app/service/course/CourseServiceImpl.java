package net.github.rtc.app.service.course;

import net.github.rtc.app.dao.course.CourseDao;
import net.github.rtc.app.dao.generic.GenericDao;
import net.github.rtc.app.model.entity.activity.ActivityEntity;
import net.github.rtc.app.service.builder.UserCourseDtoBuilder;
import net.github.rtc.app.model.dto.user.UserCourseDto;
import net.github.rtc.app.model.entity.course.Course;
import net.github.rtc.app.model.entity.course.CourseStatus;
import net.github.rtc.app.service.date.DateService;
import net.github.rtc.app.service.generic.AbstractCrudEventsService;
import net.github.rtc.app.service.news.NewsService;
import net.github.rtc.app.service.order.UserCourseOrderService;
import net.github.rtc.app.service.security.AuthorizedUserProvider;
import net.github.rtc.app.service.builder.SearchResultsBuilder;
import net.github.rtc.app.service.builder.SearchResultsMapper;
import net.github.rtc.app.model.dto.filter.CourseSearchFilter;
import net.github.rtc.app.model.dto.SearchResults;
import org.springframework.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CourseServiceImpl extends AbstractCrudEventsService<Course> implements CourseService {

    private static final String COURSE_CANNOT_BE_NULL = "course cannot be null";
    private static final String COURSE_CODE_CANNOT_BE_NULL = "course code cannot be null";
    private static final Logger LOG = LoggerFactory.getLogger(CourseServiceImpl.class.getName());

    @Autowired
    private UserCourseOrderService orderService;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private DateService dateService;
    @Autowired
    private NewsService newsService;

    @Override
    public Class<Course> getType() {
        return Course.class;
    }

    @Override
    protected GenericDao<Course> getDao() {
        return courseDao;
    }

    @Override
    public void publish(@Nonnull String courseCode, boolean isNewsCreated) {
        Assert.notNull(courseCode, COURSE_CODE_CANNOT_BE_NULL);
        final Course course = findByCode(courseCode);
        Assert.notNull(course, COURSE_CANNOT_BE_NULL);
        LOG.debug("Publishing course: {}  ", course.getCode());

        update(course, true, isNewsCreated);
    }

    @Override
    public void archive(@Nonnull String courseCode) {
        Assert.notNull(courseCode, COURSE_CODE_CANNOT_BE_NULL);
        final Course course = findByCode(courseCode);
        Assert.notNull(course, COURSE_CANNOT_BE_NULL);
        LOG.debug("Archiving course: {}  ", course.getCode());
        course.setStatus(CourseStatus.ARCHIVED);
        courseDao.update(course);
    }

    @Override
    public SearchResults<UserCourseDto> searchUserCourses(CourseSearchFilter filter) {
        final SearchResultsBuilder<Course, UserCourseDto> courseDtoSearchResultsBuilder = new SearchResultsBuilder<>();
        return courseDtoSearchResultsBuilder.setSearchResultsToTransform(search(filter)).
                setSearchResultsMapper(getCourseToCourseDtoMapper()).build();
    }

    @Override
    @Nonnull
    public UserCourseDto getUserCourseDtoByCode(String code) {
        final Course course = findByCode(code);
        return new UserCourseDtoBuilder(course).
                buildAcceptedOrdersCount(orderService.getAcceptedOrdersCount(code)).
                buildCurrentUserAssigned(isUserAssignedForCourse(code)).build();
    }

    @Override
    public void create(Course course, boolean published, boolean newsCreated) {
        if (published) {
            publish(course);
        }
        create(course);
        if (newsCreated) {
            newsService.create(course);
        }
    }

    @Override
    public void update(Course course, boolean published, boolean newsCreated) {
        if (published) {
            publish(course);
        }
        update(course);
        if (newsCreated) {
            newsService.create(course);
        }
    }

    @Override
    public void deleteByCode(String code) {
        final Course course = findByCode(code);
        if (course.getStatus() != CourseStatus.PUBLISHED) {
            super.deleteByCode(code);
        }
    }

    @Override
    protected ActivityEntity getActivityEntity() {
            return ActivityEntity.COURSE;
    }

    /**
     * Set course status as PUBLISHED and set publish date as current
     * @param course course that needs to be changed
     */
    private void publish(Course course) {
        course.setStatus(CourseStatus.PUBLISHED);
        course.setPublishDate(dateService.getCurrentDate());
    }

    /**
     * Returns true if current user is assigned for course
     * @param courseCode course code of course that needs to be checked
     * @return boolean value
     */
    private boolean isUserAssignedForCourse(String courseCode) {
        final String userCode = AuthorizedUserProvider.getAuthorizedUser().getCode();
        return orderService.findByUserCodeAndCourseCode(userCode, courseCode) != null;
    }

    /**
     * Returns an object that map list of courses to courseDTOs list
     * @return anonymous class mapper
     */
    private SearchResultsMapper<Course, UserCourseDto> getCourseToCourseDtoMapper() {
            return new SearchResultsMapper<Course, UserCourseDto>() {
            @Override
            public List<UserCourseDto> map(List<Course> searchResults) {
                final List<UserCourseDto> outputResults = new ArrayList<>();
                for (Course course: searchResults) {
                    final UserCourseDtoBuilder dtoBuilder = new UserCourseDtoBuilder(course);
                    outputResults.add(dtoBuilder.
                            buildAcceptedOrdersCount(orderService.getAcceptedOrdersCount(course.getCode())).build());
                }
                return outputResults;
            }
        };
    }

}
