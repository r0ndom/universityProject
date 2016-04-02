/*package net.github.rtc.app.dao;


import net.github.rtc.app.dao.generic.GenericDao;
import net.github.rtc.app.model.entity.AbstractPersistenceObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static junit.framework.Assert.*;


@ContextConfiguration(locations ="classpath:mvc-dao-test.xml")
public abstract class AbstractGenericDaoTest<T extends AbstractPersistenceObject> {

    @Autowired
    protected DaoTestContext daoTestContext;

    protected abstract GenericDao<T> getGenericDao();
    protected abstract ModelBuilder<T> getModelBuilder();
    protected abstract EqualityChecker getEqualityChecker();

    private T testEntity;
    private GenericDao<T> genericDao;

    @Before
    public void setUp(){
        testEntity = getModelBuilder().build();
        genericDao = getGenericDao();
    }

    @After
    public void tearDown(){
        testEntity = null;
        genericDao = null;
    }

    @Test
    @Transactional
    public void testCreate() {
        final List< ? extends AbstractPersistenceObject> initList = genericDao.findAll();
        genericDao.create(testEntity);
        assertTrue(initList.size() < genericDao.findAll().size());
    }

    @Test
    @Transactional
    public void testDeleteById(){
        testEntity = genericDao.create(testEntity);
        genericDao.delete(testEntity.getId());
        assertNull(genericDao.find(testEntity.getId()));
    }

    @Test
    @Transactional
    public void testDeleteByCode(){
        testEntity = genericDao.create(testEntity);
        genericDao.deleteByCode(testEntity.getCode());
        assertNull(genericDao.findByCode(testEntity.getCode()));
    }

    @Test
    @Transactional
    public void testFindById() {
        testEntity= genericDao.create(testEntity);
        getEqualityChecker().check(testEntity, genericDao.find(testEntity.getId()));
    }

    @Test
    @Transactional
    public void testFindByCode() {
        testEntity= genericDao.create(testEntity);
        getEqualityChecker().check(testEntity, genericDao.findByCode(testEntity.getCode()));
    }

    @Test
    @Transactional
    public void testUpdate(){
        genericDao.create(testEntity);
        testEntity.setCode("X");
        genericDao.update(testEntity);
        assertEquals("X", genericDao.find(testEntity.getId()).getCode());
    }

    @Test
    @Transactional
    public void testFindAll(){
        final List<T> initList = genericDao.findAll();
        final T testEntity = getModelBuilder().build();
        genericDao.create(testEntity);
        assertTrue(initList.size() < genericDao.findAll().size());
    }

}
*/