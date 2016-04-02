package net.github.rtc.app.controller.admin;

import net.github.rtc.app.controller.common.MenuItem;
import net.github.rtc.app.model.entity.export.ExportFormat;
import net.github.rtc.app.model.entity.export.ExportClasses;
import net.github.rtc.app.model.entity.export.ExportDetails;
import net.github.rtc.app.service.export.ExportService;
import net.github.rtc.app.utils.ExportFieldExtractor;
import net.github.rtc.app.utils.enums.EnumHelper;
import net.github.rtc.util.converter.ValidationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping("admin/export")
public class ExportController implements MenuItem {

    private static Logger log = LoggerFactory.getLogger(ExportController.class.getName());

    private static final String TYPES = "types";
    private static final String EXPORT = "export";
    private static final String FORMATS = "formats";
    private static final String VALIDATION_RULES = "validationRules";
    private static final String HEADER_KEY = "Content-Disposition";

    private static final String ROOT = "portal/admin";
    private static final String UPDATE_VIEW = "/export/exportUpdate";
    private static final String CREATE_VIEW = "/export/exportCreate";
    private static final String DETAILS_VIEW = "/export/exportDetails";
    private static final String REDIRECT_EXPORT = "redirect:/admin/export/";

    @Autowired
    private ExportService exportService;
    @Autowired
    private ValidationContext validationContext;
    @Autowired
    private LastSearchCommand lastSearchCommand;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView openCreatePage() {
        final ModelAndView mav = new ModelAndView(ROOT + CREATE_VIEW);
        mav.addObject(VALIDATION_RULES, validationContext.get(ExportDetails.class));
        return mav;
    }

    @RequestMapping(value = "/insertExport", method = RequestMethod.POST)
    public String createExport(@ModelAttribute("export") final ExportDetails export) {
        exportService.create(export);
        lastSearchCommand.dropFilter();
        return REDIRECT_EXPORT + export.getCode();
    }

    @RequestMapping(value = "/{exportCode}", method = RequestMethod.GET)
    public ModelAndView viewExport(@PathVariable final String exportCode) {
        final ModelAndView mav = new ModelAndView(ROOT + DETAILS_VIEW);
        mav.addObject(EXPORT, exportService.findByCode(exportCode));
        return mav;
    }

    @RequestMapping(value = "/update/{exportCode}", method = RequestMethod.GET)
    public ModelAndView openUpdatePage(@PathVariable final String exportCode) {
        final ModelAndView mav = new ModelAndView(ROOT + UPDATE_VIEW);
        mav.addObject(EXPORT, exportService.findByCode(exportCode));
        mav.addObject(VALIDATION_RULES, validationContext.get(ExportDetails.class));
        return mav;
    }

    @RequestMapping(value = "/updateExport", method = RequestMethod.POST)
    public String editExport(@ModelAttribute("export") final ExportDetails export) {
        exportService.update(export);
        lastSearchCommand.dropFilter();
        return REDIRECT_EXPORT + export.getCode();
    }

    @RequestMapping(value = "/delete/{exportCode}", method = RequestMethod.GET)
    public String deleteExport(@PathVariable final String exportCode) {
        exportService.deleteByCode(exportCode);
        return "redirect:/admin/search";
    }

    @RequestMapping(value = "/getFields", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getFields(@RequestParam final String selectedType) {
        return ExportFieldExtractor.getAvailableFieldList(ExportClasses.valueOf(selectedType).getValue());
    }

    @RequestMapping(value = "/download/{exportCode}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public void downloadUserExport(@PathVariable final String exportCode, final HttpServletRequest request, final HttpServletResponse response) {
        final ExportDetails exportDetails = exportService.findByCode(exportCode);
        final File downloadFile = exportService.getExport(exportDetails);
        final String agent = request.getHeader("User-Agent");
        final String correctName = exportService.getCorrectlyEncodedNameFile(exportDetails, agent);

        response.setHeader(HEADER_KEY, String.format("attachment; " + "filename=\"%s\"", correctName));

        try (final InputStream is = new FileInputStream(downloadFile)) {

            response.setContentType(Files.probeContentType(downloadFile.toPath()));
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();

        } catch (IOException ex) {
            log.info("Error writing file to output stream. Filename was '{}'", exportCode, ex);
            throw new RuntimeException("IOError writing file to output stream");
        }
    }



    @ModelAttribute(EXPORT)
    public ExportDetails getCommandObject() {
        return new ExportDetails();
    }

    @ModelAttribute(FORMATS)
    public List<String> getFormats() {
        return EnumHelper.getNames(ExportFormat.class);
    }

    @ModelAttribute(TYPES)
    public ExportClasses[] getTypes() {
        return ExportClasses.values();
    }

    @Override
    public String getMenuItem() {
        return EXPORT;
    }
}
