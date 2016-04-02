package net.github.rtc.app.controller.admin;

import net.github.rtc.app.controller.common.ResourceNotFoundException;
import net.github.rtc.app.model.dto.SearchResults;
import net.github.rtc.app.model.entity.activity.Activity;
import net.github.rtc.app.model.entity.activity.ActivityAction;
import net.github.rtc.app.model.entity.activity.ActivityEntity;
import net.github.rtc.app.model.entity.course.Course;
import net.github.rtc.app.model.entity.course.CourseStatus;
import net.github.rtc.app.model.entity.course.CourseType;
import net.github.rtc.app.model.entity.news.News;
import net.github.rtc.app.model.entity.news.NewsStatus;
import net.github.rtc.app.model.entity.export.ExportFormat;
import net.github.rtc.app.model.entity.export.ExportClasses;
import net.github.rtc.app.model.entity.export.ExportDetails;
import net.github.rtc.app.model.entity.user.RoleType;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.model.entity.user.UserStatus;
import net.github.rtc.app.service.activity.ActivityService;
import net.github.rtc.app.service.course.CourseService;
import net.github.rtc.app.service.log.LogService;
import net.github.rtc.app.service.news.NewsService;
import net.github.rtc.app.service.export.ExportService;
import net.github.rtc.app.service.user.UserService;
import net.github.rtc.app.service.security.AuthorizedUserProvider;
import net.github.rtc.app.model.dto.filter.*;
import net.github.rtc.app.utils.enums.EnumHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping(value = "/admin")
public class SearchController {

    private static final String ROOT = "portal/admin";
    private static final String SEARCH_PAGE = "/search/tables";
    private static final String NEWS = "news";
    private static final String NEWS_STATUSES = "newsStatuses";
    private static final String USER_STATUSES = "userStatuses";
    private static final String COURSE_STATUSES = "courseStatuses";
    private static final String NEWS_FILTER = "newsFilter";
    private static final String TYPES = "types";
    private static final String COURSE_FILTER = "courseFilter";
    private static final String COURSE_CATIGORIES = "courseCategories";
    private static final String USER = "user";
    private static final String USERS = "users";
    private static final String USER_AUTHORITIES = "userAuthorities";
    private static final String USER_FILTER = "userFilter";
    private static final String COURSE = "course";
    private static final String COURSES = "courses";
    private static final String EXPORT = "export";
    private static final String EXPORTS = "exports";
    private static final String EXPORT_FILTER = "exportFilter";
    private static final String EXPERTS = "experts";
    private static final String MENU_ITEM = "menuItem";
    private static final String ACTIVITY = "activity";
    private static final String ACTIVITY_FILTER = "activityFilter";
    private static final String ACTIVITY_ENTITIES = "activityEntities";
    private static final String ACTIVITY_ACTIONS = "activityActions";
    private static final String ACTIVITIES = "activities";
    private static final String LOGS_FILTER = "logsFilter";
    private static final String LOGS = "logs";
    @Autowired
    private NewsService newsService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private ExportService exportService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private LogService logService;
    @Autowired
    private LastSearchCommand lastSearchCommand;



    @RequestMapping(value = "/search/{menuItem}", method = RequestMethod.GET)
    public String searchPageWithParam(@PathVariable(MENU_ITEM) final String menuItem, RedirectAttributes redirectAttributes) {
         if (!EnumHelper.containsValue(MenuItems.class, menuItem)) {
             throw new ResourceNotFoundException();
         }
        redirectAttributes.addFlashAttribute(MENU_ITEM, menuItem);
        return "redirect:" + "/admin/search";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView searchPage() {
        final ModelAndView mav = new ModelAndView(ROOT + "/search/searchPage");
        mav.addObject(MENU_ITEM, lastSearchCommand.getMenuItem());
        return mav;
    }

    @RequestMapping(value = "/activityTable", method = RequestMethod.POST)
    public
    @ResponseBody
    ModelAndView getActivityTable(@ModelAttribute(ACTIVITY_FILTER) final ActivitySearchFilter activityFilter) {
        final ModelAndView mav = new ModelAndView(ROOT + SEARCH_PAGE + "/activitySearchTable");
        final SearchResults<Activity> results = activityService.search(activityFilter);
        mav.addAllObjects(results.getPageModel().getPageParams());
        mav.addObject(ACTIVITIES, results.getResults());
        lastSearchCommand.setLastFilter(activityFilter);
        lastSearchCommand.setMenuItem(ACTIVITY);
        return mav;
    }

    @RequestMapping(value = "/newsTable", method = RequestMethod.POST)
    public
    @ResponseBody
    ModelAndView getNewsTable(@ModelAttribute(NEWS_FILTER) final NewsSearchFilter newsFilter) {
        final ModelAndView mav = new ModelAndView(ROOT + SEARCH_PAGE + "/newsSearchTable");
        final SearchResults<News> results = newsService.search(newsFilter);
        mav.addAllObjects(results.getPageModel().getPageParams());
        mav.addObject(NEWS, results.getResults());
        lastSearchCommand.setLastFilter(newsFilter);
        lastSearchCommand.setMenuItem(NEWS);
        return mav;
    }

    @RequestMapping(value = "/courseTable", method = RequestMethod.POST)
    public
    @ResponseBody
    ModelAndView getCourseTable(@ModelAttribute(COURSE_FILTER) final CourseSearchFilter courseFilter) {
        final ModelAndView mav = new ModelAndView(ROOT + SEARCH_PAGE + "/courseSearchTable");
        final SearchResults<Course> results = courseService.search(courseFilter);
        mav.addAllObjects(results.getPageModel().getPageParams());
        mav.addObject(COURSES, results.getResults());
        mav.addObject(TYPES, EnumHelper.findAll(CourseType.class));
        mav.addObject(COURSE_STATUSES, getCourseStatuses());
        mav.addObject(COURSE_FILTER, courseFilter);
        lastSearchCommand.setLastFilter(courseFilter);
        lastSearchCommand.setMenuItem(COURSE);
        return mav;
    }

    @RequestMapping(value = "/userTable", method = RequestMethod.POST)
    public
    @ResponseBody
    ModelAndView getUserTable(@ModelAttribute(USER_FILTER) final UserSearchFilter userFilter) {
        final ModelAndView mav = new ModelAndView(ROOT + SEARCH_PAGE + "/userSearchTable");
        final SearchResults<User> results = userService.search(userFilter);
        mav.addAllObjects(results.getPageModel().getPageParams());
        mav.addObject(USERS, results.getResults());
        lastSearchCommand.setLastFilter(userFilter);
        lastSearchCommand.setMenuItem(USER);
        return mav;
    }

    @RequestMapping(value = "/exportTable", method = RequestMethod.POST)
    public
    @ResponseBody
    ModelAndView getReportTable(@ModelAttribute(EXPORT_FILTER) final ExportSearchFilter exportFilter) {
        final ModelAndView mav = new ModelAndView(ROOT + SEARCH_PAGE + "/exportSearchTable");
        final SearchResults<ExportDetails> results = exportService.search(exportFilter);
        mav.addAllObjects(results.getPageModel().getPageParams());
        mav.addObject(EXPORTS, results.getResults());
        lastSearchCommand.setLastFilter(exportFilter);
        lastSearchCommand.setMenuItem(EXPORT);
        return mav;
    }

    @RequestMapping(value = "/logsTable", method = RequestMethod.POST)
    public
    @ResponseBody
    ModelAndView getLogsTable(@ModelAttribute(LOGS_FILTER) final LogsSearchFilter logsFilter) {
        final ModelAndView mav = new ModelAndView(ROOT + SEARCH_PAGE + "/logsSearchTable");
        mav.addAllObjects(logService.search(logsFilter));
        lastSearchCommand.setLastFilter(logsFilter);
        lastSearchCommand.setMenuItem(LOGS);
        return mav;
    }

    @RequestMapping(value = "/search/dropFilter", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void dropFilter() {
        lastSearchCommand.dropFilter();
    }

    @ModelAttribute(ACTIVITY_FILTER)
    public ActivitySearchFilter getActivitySearchFilter() {
        return (ActivitySearchFilter) lastSearchCommand.getSearchFilter(ActivitySearchFilter.class);
    }

    @ModelAttribute(NEWS_FILTER)
    public NewsSearchFilter getNewsSearchFilter() {
        return (NewsSearchFilter) lastSearchCommand.getSearchFilter(NewsSearchFilter.class);
    }

    @ModelAttribute(COURSE_FILTER)
    public CourseSearchFilter getCourseSearchFilter() {
        return (CourseSearchFilter) lastSearchCommand.getSearchFilter(CourseSearchFilter.class);
    }

    @ModelAttribute(USER_FILTER)
    public UserSearchFilter getUserSearchFilter() {
        return (UserSearchFilter) lastSearchCommand.getSearchFilter(UserSearchFilter.class);
    }

    @ModelAttribute(EXPORT_FILTER)
    public ExportSearchFilter getReportSearchFilter() {
        return (ExportSearchFilter) lastSearchCommand.getSearchFilter(ExportSearchFilter.class);
    }

    @ModelAttribute(LOGS_FILTER)
    public LogsSearchFilter getLogsSearchFilter() {
        return (LogsSearchFilter) lastSearchCommand.getSearchFilter(LogsSearchFilter.class);
    }

    @ModelAttribute(NEWS_STATUSES)
    public Collection<String> getNewsStatuses() {
        return EnumHelper.getNames(NewsStatus.class);
    }

    @ModelAttribute(USER_STATUSES)
    public Collection<String> getUserStatuses() {
        return EnumHelper.getNames(UserStatus.class);
    }

    @ModelAttribute(COURSE_STATUSES)
    public Collection<String> getCourseStatuses() {
        return EnumHelper.getNames(CourseStatus.class);
    }

    @ModelAttribute(USER_AUTHORITIES)
    public Collection<String> getAuthorities() {
        return EnumHelper.getNames(RoleType.class);
    }

    @ModelAttribute("exportFormats")
    public List<String> getFormats() {
        return EnumHelper.getNames(ExportFormat.class);
    }

    @ModelAttribute("exportStats")
    public List<String> getStats() {
        return CourseStatus.getActiveStatus();
    }

    @ModelAttribute("exportTypes")
    public ExportClasses[] getTypes() {
        return ExportClasses.values();
    }

    @ModelAttribute(COURSE_CATIGORIES)
    public Map<String, String> getCategories() {
        //QA("QA"), DEV("DEV"), BA("BA")
        return  EnumHelper.createNameValueMap(CourseType.class);
    }

    @ModelAttribute(ACTIVITY_ENTITIES)
    public Map<String, String> getActivityEntities() {
        return EnumHelper.createNameValueMap(ActivityEntity.class);
    }

    @ModelAttribute(ACTIVITY_ACTIONS)
    public Map<String, String> getActivityAction() {
        return EnumHelper.createNameValueMap(ActivityAction.class);
    }

    @ModelAttribute(EXPERTS)
    public Map<String, String> getExpertUsers() {
        final Map<String, String> expertMap = new HashMap<>();
        for (User u : userService.findAllByRole(RoleType.ROLE_EXPERT)) {
            expertMap.put(u.getCode(), u.getSurnameName());
        }
        return expertMap;
    }

    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        return AuthorizedUserProvider.getAuthorizedUser();
    }

    @ModelAttribute("initPage")
    public Integer getLastPage() {
        return lastSearchCommand.getLastPage();
    }

}
