package net.github.rtc.app.service.builder;

import net.github.rtc.app.model.dto.user.UserCourseDto;
import net.github.rtc.app.model.entity.course.Course;

import javax.annotation.Nonnull;

/**
 * Object that helps to build UserCourseDto
 * @see net.github.rtc.app.model.dto.user.UserCourseDto
 */
public class UserCourseDtoBuilder {

    private UserCourseDto courseDto = new UserCourseDto();

    /**
     * Instantiates a new builder.
     * @param course the course on what UserCourseDto is based
     */
    public UserCourseDtoBuilder(Course course) {
        buildCourseFields(course);
    }

    /**
     * Initialise course fields in dto
     * @param course the course that contains required fields
     * @return this object, cannot be null
     */
    @Nonnull
    public UserCourseDtoBuilder buildCourseFields(Course course) {
        courseDto.setCapacity(course.getCapacity());
        courseDto.setCode(course.getCode());
        courseDto.setDescription(course.getDescription());
        courseDto.setEndDate(course.getEndDate());
        courseDto.setExperts(course.getExperts());
        courseDto.setName(course.getName());
        courseDto.setStartDate(course.getStartDate());
        courseDto.setStatus(course.getStatus());
        courseDto.setTypes(course.getTypes());
        courseDto.setTags(course.getTags());
        return this;
    }

    /**
     * Initialise accepted orders count in dto
     * @param acceptedOrdersCount the accepted orders count
     * @return this object, cannot be null
     */
    @Nonnull
    public UserCourseDtoBuilder buildAcceptedOrdersCount(int acceptedOrdersCount) {
        courseDto.setAcceptedOrders(acceptedOrdersCount);
        return this;
    }

    /**
     * Initialise currentUserAssigned field
     * @param currentUserAssigned  true if current user is assigned for course
     * @return this object, cannot be null
     */
    @Nonnull
    public UserCourseDtoBuilder buildCurrentUserAssigned(boolean currentUserAssigned) {
        courseDto.setCurrentUserAssigned(currentUserAssigned);
        return this;
    }

    /**
     * Return current prebuilt UserCourseDto object
     * @return the result dto, cannot be null
     */
    @Nonnull
    public UserCourseDto build() {
        return courseDto;
    }

}
