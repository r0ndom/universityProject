package net.github.rtc.app.controller.common;

import net.github.rtc.app.model.entity.user.EnglishLevel;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.service.user.UserService;
import net.github.rtc.app.utils.enums.EnumHelper;
import net.github.rtc.app.utils.propertyeditors.CustomTypeEditor;
import net.github.rtc.util.converter.ValidationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private static final String USER = "user";
    private static final String REDIRECT_LOGIN = "redirect:/login/";
    private static final String VALIDATION_RULES = "validationRules";
    private static final String VIEW_NAME = "register/register";
    private static final String ENGLISH = "english";

    @Autowired
    private ValidationContext validationContext;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView openRegisterPage() {
        final ModelAndView mav = new ModelAndView(VIEW_NAME);
        mav.addObject(VALIDATION_RULES, validationContext.get(User.class));
        return mav;
    }

    @RequestMapping(value = "/save", headers = "content-type=multipart/*", method = RequestMethod.POST)
    public String save(@ModelAttribute(USER) final User user,
                       @RequestParam(value = "uploadPhoto", required = false) MultipartFile img,
                       RedirectAttributes redirectAttributes) {
        userService.registerUser(user, img);
        return REDIRECT_LOGIN;
    }

    @ModelAttribute(USER)
    public User getCommandObject() {
        return new User();
    }

    @InitBinder(USER)
    public void initBinder(final WebDataBinder binder) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Collection.class, new CustomTypeEditor());
    }

    @ModelAttribute(ENGLISH)
    public List<EnglishLevel> getEnglish() {
        return EnumHelper.findAll(EnglishLevel.class);
    }
}
