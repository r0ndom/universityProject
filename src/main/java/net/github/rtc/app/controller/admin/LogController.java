package net.github.rtc.app.controller.admin;

import net.github.rtc.app.controller.common.MenuItem;
import net.github.rtc.app.service.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping("/admin/logs")
public class LogController implements MenuItem {

    private static Logger log = LoggerFactory.getLogger(ExportController.class.getName());
    private static final String FILE_NAME = "fileName";
    private static final String LOGS_DETAILS = "logsDetails";
    private static final String ROOT = "portal/admin";
    private static final String LOGS_DETAILS_PAGE = "/logs/logsDetails";
    private static final String HEADER_KEY = "Content-Disposition";
    private static final String LOGS = "logs";

    @Value("${logs.path}")
    private String pathFolder;

    @Autowired
    private LogService logService;

    @RequestMapping(value = "/{fileName:.+}", method = RequestMethod.GET)
    public ModelAndView getLogsContents(@PathVariable String fileName) {
        final ModelAndView modelAndView = new ModelAndView(ROOT + LOGS_DETAILS_PAGE);
        modelAndView.addObject(FILE_NAME, fileName);
        modelAndView.addObject(LOGS_DETAILS, logService.getLogData(fileName));
        return modelAndView;
    }

    @RequestMapping(value = "/download/{fileName:.+}", method = RequestMethod.GET)
    @ResponseBody
    public void downloadLogFile(@PathVariable String fileName, final HttpServletResponse response) {
        final File downloadFile = new File(pathFolder + fileName);
        response.setContentType("text/plain");
        response.setHeader(HEADER_KEY, String.format("attachment; " + "filename=\"%s\"", fileName));
        try  {
            final List<String> lines = Files.readAllLines(downloadFile.toPath(), Charset.defaultCharset());
            for (String s: lines) {
                final ServletOutputStream out = response.getOutputStream();
                out.println(s);
            }
            response.flushBuffer();
        } catch (IOException ex) {
            log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    @Override
    public String getMenuItem() {
        return LOGS;
    }
}
