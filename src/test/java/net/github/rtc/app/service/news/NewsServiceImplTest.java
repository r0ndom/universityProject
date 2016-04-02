
package net.github.rtc.app.service.news;

import net.github.rtc.app.dao.generic.GenericDao;
import net.github.rtc.app.dao.news.NewsDao;

import net.github.rtc.app.model.dto.filter.AbstractSearchFilter;
import net.github.rtc.app.model.dto.filter.NewsSearchFilter;
import net.github.rtc.app.model.entity.AbstractPersistenceObject;
import net.github.rtc.app.model.entity.activity.ActivityEntity;
import net.github.rtc.app.model.entity.course.Course;
import net.github.rtc.app.model.entity.course.CourseType;
import net.github.rtc.app.model.entity.news.News;
import net.github.rtc.app.model.entity.news.NewsStatus;
import net.github.rtc.app.service.builder.NewsBuilder;
import net.github.rtc.app.service.date.DateService;
import net.github.rtc.app.service.generic.AbstractCrudEventsServiceTest;

import net.github.rtc.app.service.generic.GenericService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
public class NewsServiceImplTest extends AbstractCrudEventsServiceTest {

    @Mock
    private NewsBuilder newsBuilder;
    @Mock
    private DateService dateService;
    @Mock
    private NewsDao newsDao;
    @InjectMocks
    private NewsServiceImpl newsService;

    private News testEntity;
    

    @Override
    protected GenericService<AbstractPersistenceObject> getGenericService() {
        return (GenericService)newsService;
    }

    @Override
    protected GenericDao<AbstractPersistenceObject> getGenericDao() {
        return (GenericDao)newsDao;
    }

    @Override
    protected ActivityEntity trueActivityEntity() {
        return ActivityEntity.NEWS;
    }

    @Override
    protected AbstractPersistenceObject getTestEntity() {
        final News news = new News();
        news.setTitle("title");
        news.setCreateDate(new Date());
        news.setStatus(NewsStatus.DRAFT);
        news.setDescription("Y");
        return news;
    }

    @Before
    public void initTestEntity()  {
        testEntity = (News) getTestEntity();
    }
    
    @Override
    protected AbstractSearchFilter getSearchFilter() {
        return new NewsSearchFilter();
    }

    @Test
    public void testSimpleCreate()  {
        when(newsDao.create(testEntity)).thenReturn(testEntity);

        assertEquals(testEntity, newsService.create(testEntity));

        verify(newsDao).create(testEntity);
    }

    @Test
    public void testCreateAndPublished()  {
        when(newsDao.create(testEntity)).thenReturn(testEntity);

        testEntity.setStatus(NewsStatus.DRAFT);
        assertEquals(newsService.create(testEntity, true).getStatus(), NewsStatus.PUBLISHED);

        verify(newsDao).create(any(News.class));
    }
    @Test
    public void testCreateAndNotPublished()  {
        when(newsDao.create(testEntity)).thenReturn(testEntity);

        testEntity.setStatus(NewsStatus.DRAFT);
        assertEquals(newsService.create(testEntity, false).getStatus(), NewsStatus.DRAFT);
        verify(newsDao).create(any(News.class));
    }

    @Test
    public void testCreateNewsForCourse()  {
        final Course course = getSimpleCourse();

        when(newsDao.create(testEntity)).thenReturn(testEntity);
        when(newsBuilder.build(any(Course.class))).thenReturn(testEntity);

        newsService.create(course);

        verify(newsDao).create(any(News.class));
    }
    
    
    @Test
    public void testSimpleUpdate() {
        when(newsDao.update(testEntity)).thenReturn(testEntity);

        assertEquals(testEntity, newsService.update(testEntity));
        verify(newsDao).update(any(News.class));
    }

    @Test
    public void testUpdateDraftToPublished() {
        when(newsDao.update(testEntity)).thenReturn(testEntity);

        testEntity.setStatus(NewsStatus.DRAFT);
        assertEquals(newsService.update(testEntity, true).getStatus(), NewsStatus.PUBLISHED);
        verify(newsDao).update(any(News.class));
    }

    @Test
    public void testUpdateDraftToDraft() {
        when(newsDao.update(testEntity)).thenReturn(testEntity);
       
        testEntity.setStatus(NewsStatus.DRAFT);
        assertEquals(newsService.update(testEntity, false).getStatus(), NewsStatus.DRAFT);
        verify(newsDao).update(any(News.class));
    }

    @Test
    public void testUpdatePublished() {
        when(newsDao.update(testEntity)).thenReturn(testEntity);
       
        testEntity.setStatus(NewsStatus.PUBLISHED);
        assertEquals(newsService.update(testEntity, false).getStatus(), NewsStatus.PUBLISHED);
        verify(newsDao).update(any(News.class));
    }
    

    @Test
    public void testFindAllByStatusDraft() throws Exception {
        List<News> newsListDraft = new ArrayList<>();
        testEntity.setCode("1");
        newsListDraft.add(testEntity);
        
        testEntity = (News) getTestEntity();
        testEntity.setCode("2");
        newsListDraft.add(testEntity);

        when(newsDao.findAllByStatus(NewsStatus.DRAFT)).thenReturn(newsListDraft);

        assertArrayEquals(newsService.findAllByStatus(NewsStatus.DRAFT).toArray(),newsListDraft.toArray());
    }

    @Test
    public void testFindAllByStatusPublish() throws Exception {
        List<News> newsListPublished = new ArrayList<>();
        testEntity = (News) getTestEntity();
        testEntity.setCode("3");
        newsListPublished.add(testEntity);
        testEntity = (News) getTestEntity();
        testEntity.setCode("4");
        newsListPublished.add(testEntity);

        when(newsDao.findAllByStatus(NewsStatus.PUBLISHED)).thenReturn(newsListPublished);

        assertArrayEquals(newsService.findAllByStatus(NewsStatus.PUBLISHED).toArray(),newsListPublished.toArray());
    }

    @Test
    public void testPublish() throws Exception {
        testEntity.setStatus(NewsStatus.DRAFT);

        when(newsDao.findByCode(CODE)).thenReturn(testEntity);
        when(newsDao.update(testEntity)).thenReturn(testEntity);

        newsService.publish(CODE);

        assertEquals(testEntity.getStatus(), NewsStatus.PUBLISHED);
    }

    
    private Course getSimpleCourse() {
        final Course course = new Course();
        course.setName("Test Course");
        course.setCapacity(1);
        course.setTypes(new HashSet<>(Arrays.asList(CourseType.BA, CourseType.QA)));
        course.setEndDate(new Date());
        course.setStartDate(new Date());
        course.setCode("COURSE-CODE");
        return course;
    }


}

