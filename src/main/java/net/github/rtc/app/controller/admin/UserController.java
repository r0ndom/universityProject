package net.github.rtc.app.controller.admin;


import net.github.rtc.app.controller.common.MenuItem;
import net.github.rtc.app.model.entity.user.EnglishLevel;
import net.github.rtc.app.model.entity.user.RoleType;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.model.entity.user.UserStatus;
import net.github.rtc.app.service.user.UserService;
import net.github.rtc.app.model.dto.filter.UserSearchFilter;
import net.github.rtc.app.utils.enums.EnumHelper;
import net.github.rtc.app.utils.propertyeditors.CustomRoleEditor;
import net.github.rtc.app.utils.propertyeditors.CustomTypeEditor;
import net.github.rtc.util.converter.ValidationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("admin/user")
public class UserController implements MenuItem {

    private static final String USER = "user";
    private static final String USER_FILTER = "userFilter";
    private static final String AUTHORITIES = "authorities";
    private static final String STATUSES = "statuses";
    private static final String ROLES = "roles";
    private static final String ENGLISH = "english";
    private static final String PROGRAMMING_LANGUAGES = "programmingLanguages";
    private static final String VALIDATION_RULES = "validationRules";

    private static final String ROOT = "portal/admin";
    private static final String REDIRECT_USER_PAGE = "redirect:/admin/user/view/";
    private static final String REDIRECT_ADMIN_SEARCH = "redirect:/admin/search";
    private static final String REDIRECT_USER_SEARCH_PAGE = "redirect:/admin/search/user";
    private static final String UPDATE_VIEW = "/user/userUpdate";
    private static final String CREATE_VIEW = "/user/userCreate";
    private static final String DETAILS_VIEW = "/user/userDetails";

    @Autowired
    private ValidationContext validationContext;
    @Autowired
    private UserService userService;
    @Autowired
    private LastSearchCommand lastSearchCommand;

    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    public ModelAndView createUser() {
        final ModelAndView mav = new ModelAndView(ROOT + CREATE_VIEW);
        mav.addObject(VALIDATION_RULES, validationContext.get(User.class));
        return mav;
    }

    @RequestMapping(value = "/save", headers = "content-type=multipart/*", method = RequestMethod.POST)
    public String save(@ModelAttribute(USER) final User user,
                       @RequestParam(value = "uploadPhoto", required = false) MultipartFile img,
                       @RequestParam(required = false) final boolean isActive) {
        userService.create(user, img, isActive);
        lastSearchCommand.dropFilter();
        return REDIRECT_USER_PAGE + user.getCode();
    }

    @RequestMapping(value = "/view/{code}", method = RequestMethod.GET)
    public ModelAndView userPage(@PathVariable final String code) {
        final ModelAndView mav = new ModelAndView(ROOT + DETAILS_VIEW);
        mav.addObject(USER, userService.findByCode(code));
        return mav;
    }

    @RequestMapping(value = "/edit/{code}", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable final String code) {
        final ModelAndView mav = new ModelAndView(ROOT + UPDATE_VIEW);
        mav.addObject(USER, userService.findByCode(code));
        mav.addObject(VALIDATION_RULES, validationContext.get(User.class));
        return mav;
    }

    @RequestMapping(value = "/update", headers = "content-type=multipart/*", method = RequestMethod.POST)
    public String update(@ModelAttribute(USER) @Valid final User user,
                         @RequestParam(value = "uploadPhoto", required = false) MultipartFile img,
                         @RequestParam(required = false) final boolean isActive) {
        userService.update(user, img, isActive);
        lastSearchCommand.dropFilter();
        return REDIRECT_USER_PAGE + user.getCode();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    public String setStatusForRemoval(@RequestParam final String userCode) throws Exception {
        userService.markUserForRemoval(userCode);
        return REDIRECT_ADMIN_SEARCH;
    }

    @RequestMapping(value = "/restore/{userCode}", method = RequestMethod.GET)
    public String setStatusActiveAndRestore(@PathVariable final String userCode) {
        userService.restoreUser(userCode);
        return REDIRECT_ADMIN_SEARCH;
    }

    @RequestMapping(value = "/inactivate/{userCode}", method = RequestMethod.GET)
    public String setStatusInactive(@PathVariable final String userCode) {
        userService.inactivateUser(userCode);
        return REDIRECT_USER_SEARCH_PAGE;
    }

    @RequestMapping(value = "/activate/{userCode}", method = RequestMethod.GET)
    public String setStatusActive(@PathVariable final String userCode) {
        userService.activateUser(userCode);
        return REDIRECT_USER_SEARCH_PAGE;
    }

    @RequestMapping(value = "/getExperts", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getExpertUsers() {
        return userService.findAllUserNameAndCode(RoleType.ROLE_EXPERT);
    }

    @RequestMapping(value = "/getAdmins", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getAdminMapDataId() {
        return userService.findAllUserNameAndCode(RoleType.ROLE_ADMIN);
    }

    @InitBinder
    public void initFilterBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(List.class, PROGRAMMING_LANGUAGES, new CustomTypeEditor());
        binder.registerCustomEditor(List.class, AUTHORITIES, new CustomRoleEditor(userService));
    }

    @ModelAttribute(value = USER)
    public User getCommandObject() {
        return new User();
    }

    @ModelAttribute(USER_FILTER)
    public UserSearchFilter getFilterUser() {
        return new UserSearchFilter();
    }

    @ModelAttribute(STATUSES)
    public Collection<String> getStatuses() {
        return EnumHelper.getNames(UserStatus.class);
    }

    @ModelAttribute(AUTHORITIES)
    public Collection<String> getAuthorities() {
        return EnumHelper.getNames(RoleType.class);
    }

    @ModelAttribute(ENGLISH)
    public List<EnglishLevel> getEnglish() {
        return EnumHelper.findAll(EnglishLevel.class);
    }

    @ModelAttribute(ROLES)
    public List<RoleType> getCategories() {
        return EnumHelper.findAll(RoleType.class);
    }

    @Override
    public String getMenuItem() {
        return USER;
    }
}
