package net.github.rtc.app.service.log;

import net.github.rtc.app.model.dto.Log;
import net.github.rtc.app.model.dto.filter.LogsSearchFilter;

import java.util.List;
import java.util.Map;

/**
 * Service that is responsible for viewing logs on server
 */
public interface LogService {

    /**
     * Get info about all logs on server
     * @return list of objects that contain info about logs
     */
    List<Log> findAllLogs();

    /**
     * Get text of the log file
     * @param fileName name of the log file
     * @return string that contains log data
     */
    String getLogData(final String fileName);

    /**
     * Search logs by created date
     * @param logsSearchFilter contains data for search
     * @return list of objects that satisfy the search results
     */
    Map<String, Object> search(LogsSearchFilter logsSearchFilter);
}
