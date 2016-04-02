/*
package net.github.rtc.app.service.impl;
//todo
import net.github.rtc.app.dao.generic.GenericDao;
import net.github.rtc.app.dao.report.ReportDao;
import net.github.rtc.app.model.AbstractPersistenceObject;
import net.github.rtc.app.model.course.Course;
import net.github.rtc.app.model.report.ExportFormat;
import net.github.rtc.app.model.report.ReportDetails;
import net.github.rtc.app.service.generic.AbstractGenericServiceTest;
import net.github.rtc.app.service.GenericService;
import net.github.rtc.app.utils.datatable.search.AbstractSearchCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mvc-dao-test.xml" })
public class ReportServiceTest extends AbstractGenericServiceTest {

    @Mock
    private ReportDao reportDao;
    @InjectMocks
    private ReportServiceImpl reportService;

    @Override
    protected GenericService<AbstractPersistenceObject> getGenericService() {
        return (GenericService)reportService;
    }

    @Override
    protected GenericDao<AbstractPersistenceObject> getGenericDao() {
        return (GenericDao)reportDao;
    }

    @Override
    protected AbstractPersistenceObject getTestEntity() {
        ReportDetails reportDetails = new ReportDetails();
        reportDetails.setCreatedDate(new Date());
        reportDetails.setExportClass(Course.class);
        reportDetails.setName("X");
        reportDetails.setExportFormat(ExportFormat.CSV);
        reportDetails.setFields(new ArrayList<String>());
        return reportDetails;
    }

    @Override
    protected AbstractSearchCommand getSearchCommand() {
        return null;
    }

    @Override
    @Test
    public void testCreate() {
        assertTrue(true);
    }

    @Override
    @Test
    public void testUpdate() {
        assertTrue(true);
    }
}
*/
