package net.github.rtc.app.controller.user;

import net.github.rtc.app.controller.common.MenuItem;
import net.github.rtc.app.model.dto.user.UserCourseDto;
import net.github.rtc.app.model.entity.course.CourseStatus;
import net.github.rtc.app.model.entity.course.CourseType;
import net.github.rtc.app.model.entity.course.TimePeriod;
import net.github.rtc.app.model.entity.order.UserCourseOrder;
import net.github.rtc.app.service.course.CourseService;
import net.github.rtc.app.service.order.UserCourseOrderService;
import net.github.rtc.app.model.dto.filter.CourseSearchFilter;
import net.github.rtc.app.model.dto.SearchResults;
import net.github.rtc.util.converter.ValidationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/user/courses")
public class CourseController implements MenuItem {

    private static final String ROOT = "portal/user";
    private static final String COURSE = "course";
    private static final String VALIDATION_RULES = "validationRules";
    private static final String COURSES = "courses";
    private static final int COURSES_PER_PAGE = 9;

    @Autowired
    private CourseSearchFilter courseSearchFilter;
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserCourseOrderService userCourseOrderService;
    @Autowired
    private ValidationContext validationContext;

    @RequestMapping(method = RequestMethod.GET)
    public String userCourses() throws Exception {
        return ROOT + "/course/courses";
    }

    @RequestMapping(value = "/courseDetails/{courseCode}", method = RequestMethod.GET)
    public ModelAndView courseDetails(@PathVariable final String courseCode) {
        final ModelAndView mav = new ModelAndView(ROOT + "/course/userCourseDetails");
        mav.addObject(COURSE, courseService.getUserCourseDtoByCode(courseCode));
        return mav;
    }

    @RequestMapping(value = "/sendOrder", method = RequestMethod.POST)
    public String sendCourseOrder(String courseCode, CourseType position) {
        userCourseOrderService.create(courseCode, position);
        return "redirect:/user/courses";
    }

    @RequestMapping(value = "/courseTable", method = RequestMethod.POST)
    public
    @ResponseBody
    ModelAndView getCourseTable(@ModelAttribute("courseFilter") final CourseSearchFilter courseFilter) {
        final ModelAndView mav = new ModelAndView(ROOT + "/course/courseTable");
        final SearchResults<UserCourseDto> results = courseService.searchUserCourses(courseFilter);
        mav.addAllObjects(results.getPageModel().getPageParams());
        mav.addObject(COURSES, results.getResults());
        mav.addObject(VALIDATION_RULES, validationContext.get(UserCourseOrder.class));
        return mav;
    }

    @RequestMapping(value = "/position/{courseCode}", method = RequestMethod.GET)
    @ResponseBody
    public Set<CourseType> getPositions(@PathVariable String courseCode) {
        return courseService.findByCode(courseCode).getTypes();
    }

    @Override
    public String getMenuItem() {
        return COURSE;
    }

    @ModelAttribute("courseTypes")
    public CourseType[] getTypes() {
        return CourseType.values();
    }

    @ModelAttribute("periods")
    public TimePeriod[] getPeriods() {
        return TimePeriod.values();
    }

    @ModelAttribute("courseFilter")
    public CourseSearchFilter getCourseSearchFilter() {
        final CourseSearchFilter filter = courseSearchFilter;
        filter.setPerPage(COURSES_PER_PAGE);
        filter.setStatus(new HashSet<>(Arrays.asList(CourseStatus.PUBLISHED)));
        return filter;
    }

    @ModelAttribute("order")
    public UserCourseOrder courseOrder() {
        return new UserCourseOrder();
    }
}
