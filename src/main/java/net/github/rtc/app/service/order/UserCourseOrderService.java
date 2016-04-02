package net.github.rtc.app.service.order;

import net.github.rtc.app.model.dto.user.ExpertOrderDto;
import net.github.rtc.app.model.entity.course.CourseType;
import net.github.rtc.app.model.entity.order.UserCourseOrder;
import net.github.rtc.app.service.generic.GenericService;
import net.github.rtc.app.model.dto.filter.OrderSearchFilter;
import net.github.rtc.app.model.dto.SearchResults;

import java.util.List;

/**
 * The service class that is responsible for operation on UserCourseOrder model class
 * @see net.github.rtc.app.model.entity.order.UserCourseOrder
 */
public interface UserCourseOrderService extends GenericService<UserCourseOrder> {

    /**
     * Search
     * @param searchFilter object that contains the params of the search
     * @return Search result that contains  DTOs
     * @see net.github.rtc.app.model.dto.user.ExpertOrderDto
     */
    SearchResults<ExpertOrderDto> searchExpertOrders(OrderSearchFilter searchFilter);

    /**
     * Get number of accepted orders for specified course
     * @param courseCode code of the course for what operation will be performed
     * @return number of orders with status ACCEPTED
     */
    int getAcceptedOrdersCount(String courseCode);

    /**
     * Accept user's course order - means set it's reply date and status ACCEPTED
     * @param orderCode code of the order for what operation will be performed
     * @return updated order
     */
    UserCourseOrder acceptOrder(final String orderCode);

    /**
     * Decline user's course order - means set it's reply date, status REJECTED and reason of the rejection
     * @param orderCode code of the order for what operation will be performed
     * @param reason the reason of rejection
     * @return updated order
     */
    UserCourseOrder rejectOrder(final String orderCode, final String reason);

    /**
     * Create order for course on specified position
     * @param courseCode code of the course for what order will be created
     * @param position position for what user wanna be applied on course
     * @return created order
     */
    UserCourseOrder create(String courseCode, CourseType position);

    /**
     * Get order with specified user code and course code
     * @param userCode user code for order
     * @param courseCode course code for order
     * @return order with mentioned params
     */
    UserCourseOrder findByUserCodeAndCourseCode(String userCode, String courseCode);

    /**
     * Get list of orders with specified user code
     * @param userCode user code for order
     * @return list of orders with mentioned param
     */
    List findLastByUserCode(String userCode);
}
