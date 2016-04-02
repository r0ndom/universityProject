package net.github.rtc.app.service.generic;

import net.github.rtc.app.dao.generic.GenericDao;

import net.github.rtc.app.model.dto.SearchResults;
import net.github.rtc.app.model.dto.filter.AbstractSearchFilter;
import net.github.rtc.app.model.entity.AbstractPersistenceObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ContextConfiguration(locations = "classpath:mvc-service-test.xml")
public abstract class AbstractGenericServiceTest {

    public static final String CODE = "X";
    @Mock
    private CodeGenerationService codeGenerationService;

    private GenericDao<AbstractPersistenceObject> genericDao;
    private GenericService<AbstractPersistenceObject> genericService;

    private AbstractPersistenceObject testEntity;

    protected abstract GenericService<AbstractPersistenceObject> getGenericService();
    protected abstract GenericDao<AbstractPersistenceObject> getGenericDao();
    protected abstract AbstractPersistenceObject getTestEntity();

    protected abstract AbstractSearchFilter getSearchFilter();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        when(codeGenerationService.generate()).thenReturn(CODE);
        genericDao = getGenericDao();
        testEntity = getTestEntity();
        genericService = getGenericService();
    }

    @After
    public void tearDown(){
        testEntity = null;
        genericDao = null;
        genericDao = getGenericDao();
        genericService = null;
    }


    @Test
    public void testDeleteByCode() {
       doAnswer(new Answer() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                testEntity = null;
                return null;
            }
        }).when(genericDao).deleteByCode(CODE);
        genericService.deleteByCode(CODE);
        assertEquals(testEntity, null);
        testEntity = getTestEntity();
    }

    @Test
    public void testFindByCode() {
        when(genericDao.findByCode(CODE)).thenReturn(testEntity);
        assertEquals(testEntity, genericService.findByCode(CODE));
    }

    @Test
    public void testCreate() {
        when(genericDao.create(testEntity)).thenReturn(testEntity);
        assertEquals(testEntity, genericService.create(testEntity));
    }

    @Test
    public void testUpdate() {
        when(genericDao.update(testEntity)).thenReturn(testEntity);
        assertEquals(testEntity, genericService.update(testEntity));
    }

    @Test
    public void testFindAll() {
        when(genericDao.findAll()).thenReturn(Arrays.asList(testEntity));
        assertEquals(Arrays.asList(testEntity), genericService.findAll());
    }

    @Test
    public void testSearch() {
        final SearchResults<AbstractPersistenceObject> results = new SearchResults();
        results.setResults(Arrays.asList(testEntity));
        AbstractSearchFilter filter =  getSearchFilter();
        when(genericDao.search(filter)).thenReturn(results);
        assertEquals(results, genericService.search(filter));
    }
}
