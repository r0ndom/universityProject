package net.github.rtc.app.controller.user;

import net.github.rtc.app.controller.common.MenuItem;
import net.github.rtc.app.model.entity.course.Course;
import net.github.rtc.app.model.entity.order.UserCourseOrder;
import net.github.rtc.app.model.entity.user.*;
import net.github.rtc.app.service.builder.UserCourseOrderDtoBuilder;
import net.github.rtc.app.service.course.CourseService;
import net.github.rtc.app.service.order.UserCourseOrderService;
import net.github.rtc.app.service.user.UserService;
import net.github.rtc.app.service.security.AuthorizedUserProvider;
import net.github.rtc.app.utils.propertyeditors.CustomTypeEditor;
import net.github.rtc.util.converter.ValidationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = { "/user/profile", "/expert/profile", "/admin/profile" })
public class ProfileController implements MenuItem {

    private static final String USER_ROOT = "portal/user";
    private static final String EXPERT_ROOT = "portal/expert";
    private static final String ADMIN_ROOT = "portal/admin";
    private static final String USER = "user";
    private static final String REDIRECT = "redirect:/";
    private static final String VALIDATION_RULES = "validationRules";
    private static final boolean IS_ACTIVE = true;

    private static final String ADMIN_ROOT_URL = "/admin/profile";
    private static final String EXPERT_ROOT_URL = "/expert/profile";
    private static final String USER_ROOT_URL = "/user/profile";

    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ValidationContext validationContext;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserCourseOrderService courseOrderService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView user() {
        final ModelAndView mav = new ModelAndView(getRoot() + "/profile/profile");
        final User user = AuthorizedUserProvider.getAuthorizedUser();
        mav.addObject(USER, user);
        mav.addObject("courses", getLastCourses(user));
        return mav;
    }

    /**
     * Process the request to get edit user form
     *
     * @return modelAndView(user/layout)
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit() {
        final User user = AuthorizedUserProvider.getAuthorizedUser();
        final ModelAndView mav = new ModelAndView(getRoot() + "/profile/editProfile");
        mav.getModelMap().addAttribute(USER, user);
        mav.addObject(VALIDATION_RULES, validationContext.get(User.class));
        return mav;
    }

    /**
     * Process the request to post entered user in the form
     *
     * @param user course object
     * @return if all is OK the redirect to view user details
     */
    @RequestMapping(value = "/update", headers = "content-type=multipart/*", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute(USER) final User user,
                               @RequestParam(value = "uploadPhoto", required = false) MultipartFile img) {
        user.setAuthorities((List<Role>) AuthorizedUserProvider.getAuthorizedUser().getAuthorities());
        userService.update(user, img, IS_ACTIVE);
        final Authentication request =  new UsernamePasswordAuthenticationToken(user, user.getPassword());
        authenticationManager.authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(request);
        return new ModelAndView("redirect:" + getRootURL());
    }

    private String getRoot() {
        final User authorizedUser = AuthorizedUserProvider.getAuthorizedUser();

        if (authorizedUser.hasRole(RoleType.ROLE_ADMIN.name())) {
            return ADMIN_ROOT;
        }
        if (authorizedUser.hasRole(RoleType.ROLE_EXPERT.name())) {
            return EXPERT_ROOT;
        }
        return USER_ROOT;
    }

    private String getRootURL() {
        final User authorizedUser = AuthorizedUserProvider.getAuthorizedUser();

        if (authorizedUser.hasRole(RoleType.ROLE_ADMIN.name())) {
            return ADMIN_ROOT_URL;
        }
        if (authorizedUser.hasRole(RoleType.ROLE_EXPERT.name())) {
            return EXPERT_ROOT_URL;
        }
        return USER_ROOT_URL;
    }

    /**
     * Binding user conditions for entry into the form conclusions
     *
     * @param binder
     */
    @InitBinder(USER)
    public void initBinder(final WebDataBinder binder) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Collection.class, new CustomTypeEditor());
    }

    @ModelAttribute("currentUserName")
    public String getCurrentUserName() {
        return AuthorizedUserProvider.getAuthorizedUserName();
    }

    @ModelAttribute("order")
    public UserCourseOrder getObject() {
        return new UserCourseOrder();
    }

    @ModelAttribute("english")
    public EnglishLevel[] getEnglish() {
        return EnglishLevel.values();
    }

    @Override
    public String getMenuItem() {
        return "profile";
    }


    public List getLastCourses(User user) {
        final List coursesDto = new LinkedList();
        UserCourseOrderDtoBuilder courseOrderDtoBuilder;
        final List courseOrders = courseOrderService.findLastByUserCode(user.getCode());
        for (Object order : courseOrders) {
            final Course course = courseService.findByCode(((UserCourseOrder) order).getCourseCode());
            courseOrderDtoBuilder = new UserCourseOrderDtoBuilder((UserCourseOrder) order, course);
            coursesDto.add(courseOrderDtoBuilder.build());
        }
        return coursesDto;
    }
}
