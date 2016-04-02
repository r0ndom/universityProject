package net.github.rtc.app.model.dto.filter;

import net.github.rtc.app.model.entity.course.Course;
import net.github.rtc.app.model.entity.course.CourseType;
import net.github.rtc.app.model.entity.order.UserCourseOrder;
import net.github.rtc.app.model.entity.order.UserRequestStatus;
import org.hibernate.criterion.*;

import java.util.Date;

public class OrderSearchFilter extends AbstractSearchFilter {

    private static final String EXPERTS = "experts";
    private static final String POSITION = "position";
    private static final String REQUEST_DATE = "requestDate";
    private static final String STATUS = "status";
    private static final String COURSE_CODE = "courseCode";
    private static final String CODE = "code";

    private CourseType courseType;
    private Date orderDate;
    private UserRequestStatus status;
    private char dateMoreLessEq;
    private String expertCode;

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public Date getOrderDate() {
        return orderDate == null ? null : new Date(orderDate.getTime());
    }

    public void setOrderDate(Date orderDate) {
        if (orderDate != null) {
            this.orderDate = new Date(orderDate.getTime());
        }
    }

    public char getDateMoreLessEq() {
        return dateMoreLessEq;
    }

    public void setDateMoreLessEq(char dateMoreLessEq) {
        this.dateMoreLessEq = dateMoreLessEq;
    }

    public UserRequestStatus getStatus() {
        return status;
    }

    public void setStatus(UserRequestStatus status) {
        this.status = status;
    }

    public String getExpertCode() {
        return expertCode;
    }

    public void setExpertCode(String expertCode) {
        this.expertCode = expertCode;
    }

    @Override
    public Order order() {
        return Order.desc(REQUEST_DATE);
    }

    @Override
    public DetachedCriteria getCriteria() {
        final DetachedCriteria criteria = DetachedCriteria.forClass(UserCourseOrder.class);
        if (courseType != null) {
            criteria.add(Restrictions.eq(POSITION, courseType));
        }
        if (orderDate != null) {
            final DateCriteriaCreator dateCriteriaCreator = new DateCriteriaCreator(REQUEST_DATE, orderDate);
            criteria.add(dateCriteriaCreator.getDateCriteria(dateMoreLessEq));
        }

        if (status != null) {
            criteria.add(Restrictions.eq(STATUS, status));
        }

        if (expertCode != null && !("").equals(expertCode)) {
            final DetachedCriteria subQuery = DetachedCriteria.forClass(Course.class);
            subQuery.createAlias(EXPERTS, EXPERTS);
            final Conjunction experts = Restrictions.conjunction();
            experts.add(Restrictions.eq(EXPERTS + "." + CODE, expertCode));
            subQuery.add(experts);
            subQuery.setProjection(Projections.property(CODE));
            criteria.add(Property.forName(COURSE_CODE).in(subQuery));
        }
        return criteria;
    }
}
