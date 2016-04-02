package net.github.rtc.app.model.dto.filter;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;

public class DateCriteriaCreator {

    private String fieldName;
    private Date date;

    public DateCriteriaCreator(String fieldName, Date date) {
        this.fieldName = fieldName;
        if (date != null) {
            this.date = new Date(date.getTime());
        }
    }

    public Criterion getDateCriteria(char dateMoreLessEq) {
        final DateTime today = new DateTime(date).withTimeAtStartOfDay();
        switch (dateMoreLessEq) {
            case '>': return Restrictions.gt(fieldName, today.toDate());
            case '=':
                final Date tomorrow = setNightTime(date);
                return Restrictions.between(fieldName, today.toDate(), tomorrow);
            case '<': return Restrictions.lt(fieldName, today.toDate());
            default: throw new IllegalArgumentException();
        }
    }

    private Date setNightTime(Date currentDate) {
        final int hour = 23;
        final int minuteSecond = 59;
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minuteSecond);
        calendar.set(Calendar.SECOND, minuteSecond);
        return calendar.getTime();
    }
}
