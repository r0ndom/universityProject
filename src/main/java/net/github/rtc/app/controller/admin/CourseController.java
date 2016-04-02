package net.github.rtc.app.controller.admin;

import net.github.rtc.app.controller.common.MenuItem;
import net.github.rtc.app.model.entity.course.Course;
import net.github.rtc.app.model.entity.course.CourseStatus;
import net.github.rtc.app.model.entity.course.CourseType;
import net.github.rtc.app.model.entity.user.RoleType;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.service.course.CourseService;
import net.github.rtc.app.service.user.UserService;
import net.github.rtc.app.utils.enums.EnumHelper;
import net.github.rtc.app.utils.propertyeditors.CustomExpertsEditor;
import net.github.rtc.app.utils.propertyeditors.CustomTagsEditor;
import net.github.rtc.app.utils.propertyeditors.CustomTypeEditor;
import net.github.rtc.util.converter.ValidationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller("coursesController")
@RequestMapping("admin/course")
public class CourseController implements MenuItem {

    private static final String COURSE = "course";
    private static final String TYPES = "types";
    private static final String STATUSES = "statuses";
    private static final String CATEGORIES = "categories";
    private static final String TAGS = "tags";
    private static final String EXPERTS = "experts";
    private static final String VALIDATION_RULES = "validationRules";

    private static final String REDIRECT = "redirect:";
    private static final String ADMIN = "/admin";
    private static final String ROOT = "portal/admin";
    private static final String VIEW = "view/";
    private static final String UPDATE_VIEW = "/course/courseUpdate";
    private static final String CREATE_VIEW = "/course/courseCreate";
    private static final String DETAILS_VIEW = "/course/courseDetails";
    private static final String NEWS_CREATED = "?newsJustCreated=";

    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private ValidationContext validationContext;
    @Autowired
    private LastSearchCommand lastSearchCommand;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        final ModelAndView mav = new ModelAndView(ROOT + CREATE_VIEW);
        mav.addObject(VALIDATION_RULES, validationContext.get(Course.class));
        return mav;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute(COURSE) final Course course,
                       @RequestParam(required = false) final boolean published,
                       @RequestParam(required = false) final boolean newsCreated) {
        courseService.create(course, published, newsCreated);
        lastSearchCommand.dropFilter();
        return REDIRECT + VIEW + course.getCode() + NEWS_CREATED + newsCreated;
    }

    @RequestMapping(value = VIEW + "{courseCode}", method = RequestMethod.GET)
    public ModelAndView single(@PathVariable final String courseCode,
                               @RequestParam(required = false) boolean newsJustCreated) {
        final ModelAndView mav = new ModelAndView(ROOT + DETAILS_VIEW);
        mav.addObject(COURSE, courseService.findByCode(courseCode));
        mav.addObject("newsInfo", newsJustCreated);
        return mav;
    }

    @RequestMapping(value = "/{courseCode}/update", method = RequestMethod.GET)
    public ModelAndView update(@PathVariable final String courseCode) {
        final ModelAndView mav = new ModelAndView(ROOT + UPDATE_VIEW);
        mav.addObject(COURSE, courseService.findByCode(courseCode));
        mav.addObject(VALIDATION_RULES, validationContext.get(Course.class));
        return mav;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@ModelAttribute(COURSE) final Course course,
                         @RequestParam(required = false) final boolean published,
                         @RequestParam(required = false) final boolean newsCreated) {
        courseService.update(course, published, newsCreated);
        lastSearchCommand.dropFilter();
        return REDIRECT + VIEW + course.getCode() + NEWS_CREATED + newsCreated;
    }

    @RequestMapping(value = "/delete/{courseCode}", method = RequestMethod.GET)
    public String delete(@PathVariable final String courseCode) {
        courseService.deleteByCode(courseCode);
        return REDIRECT + ADMIN;
    }

    @RequestMapping(value = "/publish/{courseCode}", method = RequestMethod.GET)
    public String publish(@PathVariable final String courseCode,
                          @RequestParam(required = false) final boolean newsCreated) {
        courseService.publish(courseCode, newsCreated);
        return REDIRECT + ADMIN;
    }

    @RequestMapping(value = "/archive/{courseCode}", method = RequestMethod.GET)
    public String archive(@PathVariable final String courseCode) {
        courseService.archive(courseCode);
        return REDIRECT + ADMIN;
    }

    @ModelAttribute(value = COURSE)
    public Course getCommandObject() {
        return new Course();
    }

    @ModelAttribute(CATEGORIES)
    public Map<String, String> getCategories() {
        return EnumHelper.createNameValueMap(CourseType.class);
    }

    @ModelAttribute(EXPERTS)
    public Map<String, String> getExpertUsers() {
        final Map<String, String> expertMap = new HashMap<>();
        for (User u : userService.findAllByRole(RoleType.ROLE_EXPERT)) {
            expertMap.put(u.getCode(), u.getSurnameName());
        }
        return expertMap;
    }
    @ModelAttribute(STATUSES)
    public Collection<String> getStatuses() {
        return EnumHelper.getNames(CourseStatus.class);
    }

    @InitBinder(COURSE)
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(List.class, TAGS, new CustomTagsEditor());
        binder.registerCustomEditor(List.class, TYPES, new CustomTypeEditor());
        binder.registerCustomEditor(Set.class, EXPERTS, new CustomExpertsEditor(userService));
    }

    @Override
    public String getMenuItem() {
        return COURSE;
    }
}



