/*
package net.github.rtc.app.dao.impl;
//todo

import net.github.rtc.app.dao.*;
import net.github.rtc.app.model.AbstractPersistenceObject;
import net.github.rtc.app.model.course.Course;
import net.github.rtc.app.model.course.CourseType;
import net.github.rtc.app.utils.datatable.search.CourseSearchFilter;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:mvc-dao-test.xml" )
public class CourseDaoTest extends AbstractGenericDaoTest<Course> {

    @Autowired
    private CoursesDao coursesDao;
    @Override
    protected GenericDao<Course> getGenericDao() {
        return coursesDao;
    }

    @Override
    protected ModelBuilder<Course> getModelBuilder() {
        return daoTestContext.getModelBuilder(Course.class);
    }

    @Override
    protected EqualityChecker getEqualityChecker() {
        return daoTestContext.getEqualityChecker(Course.class);
    }

    @Test
    @Transactional
    public void testSearch(){
        for(int i=0; i<5; i++) {
            getGenericDao().create(getModelBuilder().build());
        }
        final CourseSearchFilter courseSearchFilter = new CourseSearchFilter();
        courseSearchFilter.setName("Test Course");
        List<Course> courses = getGenericDao().search(courseSearchFilter).getResults();
        assertEquals(5, courses.size());
        for(AbstractPersistenceObject course: courses) {
            Course course1 = (Course)course;
            assertEquals(course1.getName(), "Test Course");
            assertEquals(course1.getTypes(), new HashSet<>(Arrays.asList(CourseType.BA, CourseType.QA)));
        }
    }
}
*/
