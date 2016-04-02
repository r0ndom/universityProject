package net.github.rtc.app.controller.expert;

import net.github.rtc.app.controller.common.MenuItem;
import net.github.rtc.app.model.dto.user.ExpertOrderDto;
import net.github.rtc.app.model.entity.course.CourseType;
import net.github.rtc.app.model.entity.order.UserRequestStatus;
import net.github.rtc.app.service.course.CourseService;
import net.github.rtc.app.service.order.UserCourseOrderService;
import net.github.rtc.app.service.user.UserService;
import net.github.rtc.app.service.security.AuthorizedUserProvider;
import net.github.rtc.app.model.dto.filter.OrderSearchFilter;
import net.github.rtc.app.model.dto.SearchResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("expert/order")
public class OrderController implements MenuItem {

    private static final String ROOT = "portal/expert";
    private static final String REDIRECT_REQUESTS = "redirect:/user/expert/order";
    private static final String ORDERS = "orders";
    private static final String COURSE_CATEGORIES = "courseCategories";
    private static final String ORDER_FILTER = "orderFilter";
    private static final String ORDER_STATUSES = "orderStatuses";
    @Autowired
    private UserCourseOrderService userCourseOrderService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    @RequestMapping(method = RequestMethod.GET)
    public String expertOrders() {
        return ROOT + "/orders/orders";
    }

    @RequestMapping(value = "/orderTable", method = RequestMethod.GET)
    public ModelAndView ordersTable(@ModelAttribute(ORDER_FILTER) OrderSearchFilter searchFilter) {
        final ModelAndView mav = new ModelAndView(ROOT + "/orders/orderTable");
        final SearchResults<ExpertOrderDto> searchResults = userCourseOrderService.searchExpertOrders(searchFilter);
        mav.addObject(ORDERS, searchResults.getResults());
        mav.addAllObjects(searchResults.getPageModel().getPageParams());
        return mav;
    }

    @RequestMapping(value = "/accept/{orderCode}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void acceptRequest(@PathVariable final String orderCode) {
        userCourseOrderService.acceptOrder(orderCode);
    }

    @RequestMapping(value = "/decline/{orderCode}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void declineRequest(@PathVariable final String orderCode, @RequestParam final String reason) {
        userCourseOrderService.rejectOrder(orderCode, reason);
    }

    @RequestMapping(value = "/user/{userCode}", method = RequestMethod.GET)
    public ModelAndView userPreview(@PathVariable String userCode) {
        final ModelAndView mav = new ModelAndView(ROOT + "/orders/userPreview");
        mav.addObject("user", userService.findByCode(userCode));
        return mav;
    }

    @RequestMapping(value = "/course/{courseCode}", method = RequestMethod.GET)
    public ModelAndView coursePreview(@PathVariable String courseCode) {
        final ModelAndView mav = new ModelAndView(ROOT + "/orders/coursePreview");
        mav.addObject("course", courseService.getUserCourseDtoByCode(courseCode));
        return mav;
    }

    @ModelAttribute(ORDER_FILTER)
    public OrderSearchFilter getCourseSearchFilter() {
        final OrderSearchFilter orderSearchFilter = new OrderSearchFilter();
        orderSearchFilter.setExpertCode(AuthorizedUserProvider.getAuthorizedUser().getCode());
        return orderSearchFilter;
    }

    @ModelAttribute(COURSE_CATEGORIES)
    public CourseType[] getCategories() {
        return  CourseType.values();
    }

    @ModelAttribute(ORDER_STATUSES)
    public UserRequestStatus[] getStats() {
        return UserRequestStatus.values();
    }

    @Override
    public String getMenuItem() {
        return ORDERS;
    }
}
