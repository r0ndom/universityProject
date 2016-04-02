package net.github.rtc.app.dao.order;

import net.github.rtc.app.dao.generic.GenericDao;
import net.github.rtc.app.model.entity.order.UserCourseOrder;

import java.util.List;

/**
 * Data access object for UserCourseOrderDao domain class
 * @see net.github.rtc.app.model.entity.order.UserCourseOrder
 */
public interface UserCourseOrderDao extends GenericDao<UserCourseOrder> {

    /**
     * Get count of accepted orders for course
     * @param courseCode code of the course for what count of accepted orders needs to be found
     * @return count for accepted orders
     */
    int getAcceptedOrdersCourseCount(String courseCode);

    /**
     * Find user course order with specified userCode and courseCode
     * @param userCode userCode criteria for search
     * @param courseCode courseCode criteria for search
     * @return founded userCode
     */
    UserCourseOrder getUserCourseOrder(String userCode, String courseCode);

    /**
     * Find last user course order with specified userCode
     * @param userCode userCode criteria for search
     * @return list of founded userCode
     */
    List getLastUserCourseOrders(String userCode);
}
