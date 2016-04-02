package net.github.rtc.app.model.dto.user;

import net.github.rtc.app.model.entity.order.UserRequestStatus;

import java.util.Date;

public class ExpertOrderDto {

    private String userCode;
    private String userName;
    private String userPhoto;

    private String courseCode;
    private String courseName;
    private Date courseStartDate;
    private Date courseEndDate;
    private int courseCapacity;
    private int courseAcceptedOrders;

    private String orderCode;
    private Date orderDate;
    private UserRequestStatus status;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getCourseStartDate() {
        return courseStartDate == null ? null : new Date(courseStartDate.getTime());
    }

    public void setCourseStartDate(Date courseStartDate) {
        if (courseStartDate != null) {
            this.courseStartDate = new Date(courseStartDate.getTime());
        }
    }

    public Date getCourseEndDate() {
        return courseEndDate == null ? null : new Date(courseEndDate.getTime());
    }

    public void setCourseEndDate(Date courseEndDate) {
        if (courseEndDate != null) {
            this.courseEndDate = new Date(courseEndDate.getTime());
        }
    }

    public int getCourseCapacity() {
        return courseCapacity;
    }

    public void setCourseCapacity(int courseCapacity) {
        this.courseCapacity = courseCapacity;
    }

    public int getCourseAcceptedOrders() {
        return courseAcceptedOrders;
    }

    public void setCourseAcceptedOrders(int courseAcceptedOrders) {
        this.courseAcceptedOrders = courseAcceptedOrders;
    }

    public Date getOrderDate() {
        return orderDate == null ? null : new Date(orderDate.getTime());
    }

    public void setOrderDate(Date orderDate) {
        if (orderDate != null) {
            this.orderDate = new Date(orderDate.getTime());
        }
    }

    public UserRequestStatus getStatus() {
        return status;
    }

    public void setStatus(UserRequestStatus status) {
        this.status = status;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExpertOrderDto)) {
            return false;
        }

        final ExpertOrderDto that = (ExpertOrderDto) o;

        if (courseAcceptedOrders != that.courseAcceptedOrders) {
            return false;
        }
        if (courseCapacity != that.courseCapacity) {
            return false;
        }
        if (courseCode != null ? !courseCode.equals(that.courseCode) : that.courseCode != null) {
            return false;
        }
        if (courseEndDate != null ? !courseEndDate.equals(that.courseEndDate) : that.courseEndDate != null) {
            return false;
        }

        if (courseName != null ? !courseName.equals(that.courseName) : that.courseName != null) {
            return false;
        }
        if (courseStartDate != null ? !courseStartDate.equals(that.courseStartDate) : that.courseStartDate != null) {
            return false;
        }
        if (orderCode != null ? !orderCode.equals(that.orderCode) : that.orderCode != null) {
            return false;
        }
        if (orderDate != null ? !orderDate.equals(that.orderDate) : that.orderDate != null) {
            return false;
        }
        if (status != that.status) {
            return false;
        }
        if (userCode != null ? !userCode.equals(that.userCode) : that.userCode != null) {
            return false;
        }
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) {
            return false;
        }
        if (userPhoto != null ? !userPhoto.equals(that.userPhoto) : that.userPhoto != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int hashCodeConst = 31;

        int result = userCode != null ? userCode.hashCode() : 0;
        result = hashCodeConst * result + (userName != null ? userName.hashCode() : 0);
        result = hashCodeConst * result + (userPhoto != null ? userPhoto.hashCode() : 0);
        result = hashCodeConst * result + (courseCode != null ? courseCode.hashCode() : 0);
        result = hashCodeConst * result + (courseName != null ? courseName.hashCode() : 0);
        result = hashCodeConst * result + (courseStartDate != null ? courseStartDate.hashCode() : 0);
        result = hashCodeConst * result + (courseEndDate != null ? courseEndDate.hashCode() : 0);
        result = hashCodeConst * result + courseCapacity;
        result = hashCodeConst * result + courseAcceptedOrders;
        result = hashCodeConst * result + (orderCode != null ? orderCode.hashCode() : 0);
        result = hashCodeConst * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = hashCodeConst * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
