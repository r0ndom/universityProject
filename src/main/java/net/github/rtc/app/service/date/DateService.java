package net.github.rtc.app.service.date;

import java.util.Date;

/**
 * Class that helps to work with dates
 */
public interface DateService {
    /**
     *
     * @return current Date
     */
    Date getCurrentDate();

    /**
     * This method should add days to existing Date variable
     * @param oldDate date you need to change
     * @param days number of days that would be added
     * @return old date with added days
     */
    Date addDays(Date oldDate, final int days);

    /**
     * Calculates dates difference in month
     * Example getMothPeriod(02.03.2015, 13.05.2015) returns 2
     * @param startDate
     * @param endDate
     * @return difference in month as number
     */
    int getMothPeriod(Date startDate, Date endDate);
}
