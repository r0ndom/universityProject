package net.github.rtc.app.service.export;

import net.github.rtc.app.dao.export.ExportDao;
import net.github.rtc.app.dao.generic.GenericDao;
import net.github.rtc.app.model.dto.filter.AbstractSearchFilter;
import net.github.rtc.app.model.entity.AbstractPersistenceObject;
import net.github.rtc.app.model.entity.activity.ActivityEntity;
import net.github.rtc.app.model.entity.export.ExportClasses;
import net.github.rtc.app.model.entity.export.ExportDetails;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.service.builder.ExportBuilder;
import net.github.rtc.app.service.date.DateService;
import net.github.rtc.app.service.generic.AbstractCrudEventsServiceTest;
import net.github.rtc.app.service.generic.CodeGenerationService;
import net.github.rtc.app.service.generic.GenericService;
import net.github.rtc.app.service.generic.ModelService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Map;


import static junit.framework.Assert.assertNull;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:mvc-service-test.xml")
public class ExportServiceImplTest {
    @Mock
    private ExportDao exportDao;
    @Mock
    private DateService dateService;
    @Mock
    private ExportBuilder exportBuilder;
    @Mock
    private Map<Class, ? extends ModelService> serviceHolder;
    @Mock
    private CodeGenerationService codeGenerationService;
    @InjectMocks
    private ExportServiceImpl exportService;

    @Before
    public void prepareData() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(exportService, "exportPath", "path");
    }

    @Test
    public void testCompileReport() throws NoSuchFieldException {
        ExportDetails export = new ExportDetails();
        init(export);
        Mockito.doNothing().when(exportBuilder).build(any(ExportDetails.class), anyList(), anyString());
        ExportDetails details = exportService.compileExport(export);
        assertNotNull(details);
    }

    @Test
    public void testCompileReportNoFile() throws NoSuchFieldException {
        ExportDetails export = new ExportDetails();
        init(export);
        Mockito.doThrow(NoSuchFieldException.class).when(exportBuilder).build(any(ExportDetails.class), anyList(), anyString());
        ExportDetails details = exportService.compileExport(export);
        assertNull(details);
    }

    @Test
    public void testCreate() throws NoSuchFieldException {
        ExportDetails export = new ExportDetails();
        init(export);
        when(exportDao.create(export)).thenReturn(export);
        ExportDetails details = exportService.create(export);
        assertNotNull(details);
    }

    @Test
    public void testCreateFail() throws NoSuchFieldException {
        ExportDetails export = new ExportDetails();
        init(export);
        when(exportDao.create(export)).thenThrow(Exception.class);
        ExportDetails details = exportService.create(export);
        assertNull(details);
    }

    @Test
    public void testUpdate() throws NoSuchFieldException {
        ExportDetails export = new ExportDetails();
        init(export);
        when(exportDao.update(export)).thenReturn(export);
        ExportDetails details = exportService.update(export);
        assertNotNull(details);
    }

    @Test
    public void testUpdateFail() throws NoSuchFieldException {
        ExportDetails export = new ExportDetails();
        init(export);
        when(exportDao.update(export)).thenThrow(Exception.class);
        ExportDetails details = exportService.update(export);
        assertNull(details);
    }

    private void init(ExportDetails export) {
        export.setExportClass(ExportClasses.Course);
        ModelService serviceMock = mock(ModelService.class);
        when(serviceHolder.get(export.getExportClass().getValue())).thenReturn(serviceMock);
        when(serviceMock.findAll()).thenReturn(new ArrayList());
    }
}
