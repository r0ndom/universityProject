package net.github.rtc.app.controller.common;

import net.github.rtc.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private  static final String LOGIN_LOGIN = "login/login";

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        return new ModelAndView(LOGIN_LOGIN);
    }

    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public ModelAndView loginError(final ModelMap model) {
        if (!isAnonymous()) {
            throw new ResourceNotFoundException();
        }

        final ModelAndView mav = new ModelAndView(LOGIN_LOGIN, model);
        mav.addObject("error", "true");
        return mav;
    }

    @RequestMapping(value = "/mailExist/", method = RequestMethod.POST)
    @ResponseBody
    public boolean mailExist(@RequestParam final String email,
                             @RequestParam final String currentEmail) {
        if (email.equals(currentEmail)) {
            return true;
        }
        return !userService.isEmailExist(email);
    }

    private boolean isAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof AnonymousAuthenticationToken;
    }

}
