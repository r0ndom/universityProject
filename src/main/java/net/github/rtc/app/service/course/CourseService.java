package net.github.rtc.app.service.course;

import net.github.rtc.app.model.dto.user.UserCourseDto;
import net.github.rtc.app.model.entity.course.Course;
import net.github.rtc.app.service.generic.GenericService;
import net.github.rtc.app.service.generic.ModelService;
import net.github.rtc.app.model.dto.filter.CourseSearchFilter;
import net.github.rtc.app.model.dto.SearchResults;

/**
 * The service class that is responsible for operation on Course model class
 * @see net.github.rtc.app.model.entity.course.Course
 */
public interface CourseService extends ModelService<Course>, GenericService<Course> {

    /**
     * Publish course and possibly create news with it
     * @param courseCode code of the course that needs to be published
     * @param isNewsCreated flag that determines if news needs to be created
     */
    void publish(String courseCode, final boolean isNewsCreated);

    /**
     * Set course status to archived and update
     * @param courseCode code of the course that needs to be archived
     */
    void archive(String courseCode);

    /**
     * Search courses for user and present it as DTO
     * @param filter object that contains search params
     * @see net.github.rtc.app.model.dto.filter.CourseSearchFilter
     *
     * @return search results for user view presented as DTO
     * @see net.github.rtc.app.model.dto.user.UserCourseDto
     */
    SearchResults<UserCourseDto> searchUserCourses(CourseSearchFilter filter);

    /**
     * Get a single course for user and present it as DTO
     * @param code code of the course that needs to be found
     * @return course for user view presented as DTO
     * @see net.github.rtc.app.model.dto.user.UserCourseDto
     */
    UserCourseDto getUserCourseDtoByCode(String code);

    /**
     * Create course and possibly publish and create news about it
     * @param course actually the course that needs to be created
     * @param isPublished flag that determines if news needs to be published
     * @param newsCreated flag that determines if news needs to be created
     */
    void create(Course course, final boolean isPublished, final boolean newsCreated);

    /**
     * Update course and possibly publish and create news about it
     * @param course actually the course that needs to be updated
     * @param isPublished flag that determines if news needs to be published
     * @param newsCreated flag that determines if news needs to be created
     */
    void update(Course course, final boolean isPublished, final boolean newsCreated);
}
