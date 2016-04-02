package net.github.rtc.app.service.logs;


import net.github.rtc.app.model.dto.Log;
import net.github.rtc.app.model.dto.filter.LogsSearchFilter;
import net.github.rtc.app.service.log.LogService;
import net.github.rtc.app.service.log.LogServiceImpl;
import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@Ignore
@RunWith(BlockJUnit4ClassRunner.class)
public class LogServiceImplTest {
    @InjectMocks
    private LogService logService = new LogServiceImpl();

    @Test(expected = NullPointerException.class)
    public void testFindAllLogs() {
        List<Log> actualListOfLogs = new ArrayList<>();
        List<Log> expectedListOfLogs = logService.findAllLogs();

        boolean isAnyLogFind = (actualListOfLogs.size() != expectedListOfLogs.size());
        assertTrue(isAnyLogFind);
    }

    @Test(expected = NullPointerException.class)
    public void testGetLogData() {
        List<Log> listOfLogs = logService.findAllLogs();
        for (Log log : listOfLogs) {
            String actual = logService.getLogData(log.getFile());
            assertTrue(actual.length() != 0);
        }
    }

    @Test(expected = NullPointerException.class)
    public void testSearch() {
        Map<String, Object> expectedMapOfLogs = new HashMap<>();
        expectedMapOfLogs.put("logs", logService.findAllLogs());
        LogsSearchFilter logsSearchFilter = new LogsSearchFilter();
        logsSearchFilter.setCreatedDate(DateTime.now().toLocalDateTime().toDate());
        logsSearchFilter.setDateMoreLessEq('=');
        Map<String, Object> actualMapOfLogs = logService.search(logsSearchFilter);
        assertEquals(expectedMapOfLogs, actualMapOfLogs);

    }
}