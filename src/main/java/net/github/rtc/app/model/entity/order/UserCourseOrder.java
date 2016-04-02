package net.github.rtc.app.model.entity.order;

import com.sun.istack.NotNull;
import net.github.rtc.app.model.entity.AbstractPersistenceObject;
import net.github.rtc.app.model.entity.course.CourseType;
import net.github.rtc.util.annotation.validation.Validatable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Validatable
public class UserCourseOrder extends AbstractPersistenceObject {

    @Column
    private String userCode;

    @Column
    private String courseCode;

    @Column
    private Date requestDate;

    @Column
    private Date responseDate;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private CourseType position;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRequestStatus status = UserRequestStatus.PENDING;

    @Column
    @Size(min = 1)
    private String rejectReason;

    public UserCourseOrder(
      final String userCode,
      final String courseCode,
      final Date requestDate,
      final Date responseDate,
      final CourseType position,
      final UserRequestStatus status,
      final String rejectReason) {
        this.userCode = userCode;
        this.courseCode = courseCode;
        if (requestDate != null) {
            this.requestDate = new Date(requestDate.getTime());
        }
        if (responseDate != null) {
            this.responseDate = new Date(responseDate.getTime());
        }
        this.position = position;
        this.status = status;
        this.rejectReason = rejectReason;
    }

    public UserCourseOrder() {
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(final String userCode) {
        this.userCode = userCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(final String courseCode) {
        this.courseCode = courseCode;
    }

    public Date getResponseDate() {
        return responseDate == null ? null : new Date(responseDate.getTime());
    }

    public void setResponseDate(final Date responseDate) {
        if (responseDate != null) {
            this.responseDate = new Date(responseDate.getTime());
        }
    }

    public Date getRequestDate() {
        return requestDate == null ? null : new Date(requestDate.getTime());
    }

    public void setRequestDate(final Date requestDate) {
        if (requestDate != null) {
            this.requestDate = new Date(requestDate.getTime());
        }
    }

    public UserRequestStatus getStatus() {
        return status;
    }

    public void setStatus(final UserRequestStatus status) {
        this.status = status;
    }

    public CourseType getPosition() {
        return position;
    }

    public void setPosition(final CourseType position) {
        this.position = position;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(final String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
