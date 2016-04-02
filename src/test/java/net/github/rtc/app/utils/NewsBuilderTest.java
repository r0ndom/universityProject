package net.github.rtc.app.utils;

import net.github.rtc.app.model.entity.course.Course;
import net.github.rtc.app.model.entity.course.CourseType;
import net.github.rtc.app.model.entity.news.News;
import net.github.rtc.app.model.entity.news.NewsStatus;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.service.builder.NewsBuilder;
import net.github.rtc.app.service.builder.TemplateStringBuilder;
import net.github.rtc.app.service.date.DateService;
import net.github.rtc.app.service.security.AuthorizedUserProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.when;


@RunWith(BlockJUnit4ClassRunner.class)
public class NewsBuilderTest {

    public static final String TEST_STRING = "test string";

    @Mock
    private DateService dateService;
    @Mock
    private TemplateStringBuilder templateStringBuilder;
    @InjectMocks
    private NewsBuilder newsBuilder = new NewsBuilder();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void prepareUsersList(){
        Authentication auth = new UsernamePasswordAuthenticationToken(new User(), null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    public void testNewsCreation() {
        when(templateStringBuilder.build(any(String.class), anyMap())).thenReturn(TEST_STRING);

        final User author = getAuthor();
        final Course course = getBaCourse();
        final Date creationDate = new Date();
        final News news = newsBuilder.build(course);

        assertEquals(news.getAuthor(), author);
        assertEquals(creationDate, news.getCreateDate());
        assertEquals(NewsStatus.DRAFT, news.getStatus());
        assertEquals(TEST_STRING, news.getTitle());
        assertEquals(TEST_STRING, news.getDescription());
    }

    private User getAuthor() {
        return AuthorizedUserProvider.getAuthorizedUser();
    }

    private Course getBaCourse() {
        final Course course =  new Course();
        course.setName("BAtestCourse");
        final Set<CourseType> courseTypes = new HashSet<>();
        courseTypes.add(CourseType.BA);
        course.setTypes(courseTypes);
        return course;
    }
}
