package net.github.rtc.app.model.dto.user;

import net.github.rtc.app.model.entity.order.UserRequestStatus;
import net.github.rtc.app.model.entity.user.User;

import java.util.Date;
import java.util.Set;

public class UserCourseOrderDto {
    private String code;
    private String name;
    private Set<User> experts;
    private Date startDate;
    private Date endDate;
    private UserRequestStatus status;

    public Set<User> getExperts() {
        return experts;
    }

    public void setExperts(Set<User> experts) {
        this.experts = experts;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public UserRequestStatus getStatus() {
        return status;
    }

    public void setStatus(UserRequestStatus status) {
        this.status = status;
    }
}
