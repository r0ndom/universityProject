package net.github.rtc.app.service.builder;

import net.github.rtc.app.model.dto.user.ExpertOrderDto;
import net.github.rtc.app.model.entity.course.Course;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.model.entity.order.UserCourseOrder;

import javax.annotation.Nonnull;

/**
 * Object that helps to build ExpertOrderDto
 * @see net.github.rtc.app.model.dto.user.ExpertOrderDto
 */
public class ExpertOrderDtoBuilder {

    private ExpertOrderDto orderDto = new ExpertOrderDto();

    /**
     * Initialise dto fields that depends on UserCourseOrder object
     * @param order the order that contains necessary fields
     * @return this object, cannot be null
     */
    @Nonnull
    public ExpertOrderDtoBuilder buildOrderFields(UserCourseOrder order) {
        orderDto.setOrderCode(order.getCode());
        orderDto.setStatus(order.getStatus());
        orderDto.setOrderDate(order.getRequestDate());

        return this;
    }

    /**
     * Initialise dto fields that depends on Course object
     * @param course the course that contains necessary fields
     * @return this object, cannot be null
     */
    @Nonnull
    public ExpertOrderDtoBuilder buildCourseFields(Course course) {
        orderDto.setCourseCode(course.getCode());
        orderDto.setCourseName(course.getName());
        orderDto.setCourseStartDate(course.getStartDate());
        orderDto.setCourseEndDate(course.getEndDate());
        orderDto.setCourseCapacity(course.getCapacity());
        return this;
    }

    /**
     * Initialise dto fields that depends on User object.
     * @param user the user that contains necessary fields
     * @return this object, cannot be null
     */
    @Nonnull
    public ExpertOrderDtoBuilder buildUserFields(User user) {
        orderDto.setUserCode(user.getCode());
        orderDto.setUserName(user.getSurnameName());
        orderDto.setUserPhoto(user.getPhoto());
        return this;
    }

    /**
     * Initialise count of accepted orders for course on what the order is send
     * @param acceptedOrders count of accepted orders
     * @return this object, cannot be null
     */
    @Nonnull
    public ExpertOrderDtoBuilder buildAcceptedOrders(int acceptedOrders) {
        orderDto.setCourseAcceptedOrders(acceptedOrders);
        return this;
    }

    /**
     * Return current prebuilt ExpertOrderDto object
     * @return the result dto, cannot be null
     */
    @Nonnull
    public ExpertOrderDto get() {
        return orderDto;
    }
}
