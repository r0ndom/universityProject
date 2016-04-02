package net.github.rtc.app.service.date;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class DateServiceImpl implements DateService {

    private static final int TERM = 14;

    @Override
    public Date getCurrentDate() {
        return DateTime.now().toLocalDateTime().toDate();
    }

    @Override
    public Date addDays(Date oldDate, final int days) {
        return new DateTime(oldDate).plusDays(days).toDate();
    }

    @Override
    public int getMothPeriod(final Date startDate, final Date endDate) {
        int months = new Period(new DateTime(startDate), new DateTime(endDate), PeriodType.months()).getMonths();
        if (months == 0) {
            ++months;
        } else {
            int days = new Period(new DateTime(startDate), new DateTime(endDate), PeriodType.days()).getDays();
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            for (int i = 0; i < months; i++) {
                final int currentMonthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                days = days - currentMonthDays;
                calendar.add(Calendar.MONTH, 1);
            }
            if (days > TERM) {
                ++months;
            }
        }
        return months;
    }
}
