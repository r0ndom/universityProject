package net.github.rtc.app.model.dto.user;

import net.github.rtc.app.model.entity.course.CourseStatus;
import net.github.rtc.app.model.entity.course.CourseType;
import net.github.rtc.app.model.entity.course.Tag;
import net.github.rtc.app.model.entity.user.User;

import java.util.Date;
import java.util.Set;

public class UserCourseDto {

    private String code;
    private Integer capacity;
    private Integer acceptedOrders;
    private String name;
    private Set<CourseType> types;
    private Date startDate;
    private Date endDate;
    private CourseStatus status;
    private String description;
    private Set<User> experts;
    private boolean currentUserAssigned;
    private Set<Tag> tags;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CourseType> getTypes() {
        return types;
    }

    public void setTypes(Set<CourseType> types) {
        this.types = types;
    }

    public Date getStartDate() {
        return startDate == null ? null : new Date(startDate.getTime());
    }

    public void setStartDate(Date startDate) {
        if (startDate != null) {
            this.startDate = new Date(startDate.getTime());
        }
    }

    public Date getEndDate() {
        return endDate == null ? null : new Date(endDate.getTime());
    }

    public void setEndDate(Date endDate) {
        if (endDate != null) {
            this.endDate = new Date(endDate.getTime());
        }
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getExperts() {
        return experts;
    }

    public void setExperts(Set<User> experts) {
        this.experts = experts;
    }

    public Integer getAcceptedOrders() {
        return acceptedOrders;
    }

    public void setAcceptedOrders(Integer acceptedOrders) {
        this.acceptedOrders = acceptedOrders;
    }

    public boolean isCurrentUserAssigned() {
        return currentUserAssigned;
    }
    //for freemarker
    public boolean getCurrentUserAssigned() {
        return currentUserAssigned;
    }

    public void setCurrentUserAssigned(boolean currentUserAssigned) {
        this.currentUserAssigned = currentUserAssigned;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
