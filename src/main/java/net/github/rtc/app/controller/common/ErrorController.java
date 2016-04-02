package net.github.rtc.app.controller.common;

import com.google.common.base.Throwables;
import net.github.rtc.app.model.entity.user.RoleType;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ErrorController {
    public static final String ERROR = "error";
    private static final String JAVAX = "javax";
    private static final String DOT_SERVLET = ".servlet";
    private static final String ADMIN_ERROR_PAGE = "portal/admin/error";
    private static final String USER_ERROR_PAGE = "portal/user/error";
    private static final String ERROR_PAGE = "error/error";
    private static final String ERROR_TTL = "errorTitle";
    private static final String ERROR_MSG = "errorMessage";
    private static final String ERROR_CAS = "errorCause";

    @Autowired
    private UserService userService;

    @RequestMapping("error500")
    public ModelAndView redirectToErrorPage500(final HttpServletRequest request) {
        final ModelAndView mnv = new ModelAndView(getUserView(request));
        mnv.addObject(ERROR_TTL, "Critical Error");
        mnv.addObject(ERROR_MSG, "Error content");
        mnv.addObject(ERROR_CAS, getFullMessage(request));
        return mnv;
    }

    @RequestMapping("error404")
    public ModelAndView redirectToErrorPage404(final HttpServletRequest request) {
        final ModelAndView mnv = new ModelAndView(getUserView(request));
        mnv.addObject(ERROR_TTL, "404 Error: Page not found");
        mnv.addObject(ERROR_MSG, "Please use menu from the left side bar or contact to administrator.");
        mnv.addObject(ERROR_CAS, getFullMessage(request));
        return mnv;
    }

    @RequestMapping("error403")
    public ModelAndView redirectToErrorPage403(final HttpServletRequest request) {
        final ModelAndView mnv = new ModelAndView(getUserView(request));
        mnv.addObject(ERROR_TTL, "403 Error: Access is denied");
        mnv.addObject(ERROR_MSG, "You don't have permission to view this directory or page using the credentials that you supplied.");
        mnv.addObject(ERROR_CAS, getFullMessage(request));
        return mnv;
    }

    private String getFullMessage(final HttpServletRequest request) {
        final Integer statusCode = (Integer) request.getAttribute(
                JAVAX + DOT_SERVLET + ".error.status_code");
        final Throwable throwable = (Throwable) request.getAttribute(
                JAVAX + DOT_SERVLET + ".error.exception");
        final String exceptionMessage = getExceptionMessage(throwable,
                statusCode);

        String requestUri = (String) request.getAttribute(
                JAVAX + DOT_SERVLET + ".error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }
        return formatErrorMessage(statusCode, requestUri, exceptionMessage);
    }

    private String getExceptionMessage(
            final Throwable throwable, final Integer statusCode) {
        if (throwable != null) {
            return Throwables.getRootCause(throwable).getMessage();
        }
        final HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        return httpStatus.getReasonPhrase();
    }

    private String formatErrorMessage(
            final Integer statusCode, final String requestUri, final String message) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<!--");
        sb.append("\nERROR DETAILS");
        sb.append("\nError code:    ").append(statusCode);
        sb.append("\nReturned for:  ").append(requestUri);
        sb.append("\nError message: ").append(message);
        sb.append("\n-->");
        return sb.toString();
    }

    private String getUserView(final HttpServletRequest request) {
        final HttpSession session = request.getSession(true);

        final User user
                = userService.loadUserByUsername(
                ((SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName());
        if (user.hasRole(RoleType.ROLE_ADMIN.name())) {
            return ADMIN_ERROR_PAGE;
        } else {
            return USER_ERROR_PAGE;
        }
    }
}
