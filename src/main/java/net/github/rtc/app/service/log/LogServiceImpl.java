package net.github.rtc.app.service.log;

import net.github.rtc.app.model.dto.Log;
import net.github.rtc.app.model.dto.filter.LogsSearchFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class LogServiceImpl implements LogService {

    private static final int BEGIN_INDEX = 22;
    private static final int BYTES = 1024;

    @Value("${logs.path}")
    private String pathFolder;

    @Override
    public List<Log> findAllLogs() {
        final File folder = new File(pathFolder);
        final File[] listOfFiles = folder.listFiles();
        final List<Log> listOfLogs = new ArrayList<>();
        if (listOfFiles != null) {
            for (final File fileEntry : listOfFiles) {
                if (fileEntry.isFile()) {
                    try {
                        listOfLogs.add(new Log(fileEntry.getPath().substring(BEGIN_INDEX), getSize(fileEntry), getCreatedDate(fileEntry.getPath())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Collections.sort(listOfLogs, new Comparator<Log>() {
            @Override
            public int compare(Log o1, Log o2) {
                return (-1) * o1.getCreatedDate().compareTo(o2.getCreatedDate());
            }
        });
        return listOfLogs;
    }

    @Override
    public String getLogData(final String fileName) {
        final StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(pathFolder + fileName), Charset.defaultCharset()))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                builder.append(currentLine);
                builder.append(System.lineSeparator());
            }
        } catch (IOException e) {
            builder.append(e);
        }
        return builder.toString();
    }

    /**
     * Get log file size in Megabytes
     * @param file file for what size will be calculated
     * @return size in string with Mb suffix
     */
    private String getSize(final File file) {
        String size = "";
        if (file.exists()) {
            final int sizeInMegabytes = (int) ((file.length() / BYTES) / BYTES);
            size = sizeInMegabytes + "Mb";
        }
        return size;
    }

    /**
     * Get creation date of log file
     * @param filePath path of the file
     * @return file created date
     * @throws IOException if file not found
     */
    private Date getCreatedDate(final String filePath) throws IOException {
        final Path file = Paths.get(filePath);
        final BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);
        return new Date(attributes.creationTime().toMillis());
    }

    @Override
    public Map<String, Object> search(LogsSearchFilter logsSearchFilter) {
        List<Log> logs;
        if (logsSearchFilter.getCreatedDate() == null) {
            logs = findAllLogs();
        } else {
            Date checkedLogCreatedDate;
            Date logCreatedDate;
            checkedLogCreatedDate = parseDate(logsSearchFilter.getCreatedDate());
            logs = new ArrayList<>();
            for (Log log: findAllLogs()) {
                logCreatedDate = parseDate(log.getCreatedDate());
                switch (logsSearchFilter.getDateMoreLessEq()) {
                    case '=':
                        if (logCreatedDate.equals(checkedLogCreatedDate)) {
                            logs.add(log);
                        }
                        break;
                    case '<':
                        if (logCreatedDate.before(checkedLogCreatedDate)) {
                            logs.add(log);
                        }
                        break;
                    case '>':
                        if (logCreatedDate.after(checkedLogCreatedDate)) {
                            logs.add(log);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return getLogsPageParams(logs, logsSearchFilter);
    }

    /**
     * Get params of the logs table on logs search page
     * @param logs contains logs for logs search page
     * @param logsSearchFilter contains basic params for logs table on logs search page
     * @return maps of objects that contain params about logs table on logs search page
     */
    private Map<String, Object> getLogsPageParams(List<Log> logs, LogsSearchFilter logsSearchFilter) {
        int begin = (logsSearchFilter.getPage() - 1) * logsSearchFilter.getPerPage();
        int end = begin + logsSearchFilter.getPerPage();
        if (end > logs.size()) {
            end = logs.size();
        }
        final Map<String, Object> map = new HashMap<>();
        map.put("logs", logs.subList(begin, end));
        map.put("currentPage", logsSearchFilter.getPage());
        final int countPages = logs.size() / logsSearchFilter.getPerPage() + ((logs.size() % logsSearchFilter.getPerPage() == 0) ? 0 : 1);
        map.put("lastPage", countPages);
        if (logsSearchFilter.getPage() == countPages) {
            begin = Math.max(1, logsSearchFilter.getPage() - logsSearchFilter.getPageOffset() - 1);
            end = logsSearchFilter.getPage();
        } else {
            begin = Math.max(1, logsSearchFilter.getPage() - logsSearchFilter.getPageOffset());
            if (countPages == logsSearchFilter.getMaxOffset()) {
                end = logsSearchFilter.getMaxOffset();
            } else {
                end = Math.min(begin + logsSearchFilter.getPageOffset(), countPages);
            }
        }
        map.put("beginIndex", begin);
        map.put("endIndex", end);
        return map;
    }

    /**
     *
     * @param date what will be parsed to format dd-MMM-yyyy
     * @return parse date
     */
    private Date parseDate(Date date) {
        Date pDate = null;
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            pDate = dateFormat.parse(dateFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return pDate;
    }
}
