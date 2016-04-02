package net.github.rtc.app.service.date;

import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class DateServiceImplTest {

    @InjectMocks
    DateService dateService = new DateServiceImpl();

    @Test
    public void testGetCurrentDate() throws Exception {
        final int twoMinutes = 2 * 60 * 1000;
        final Date expected =DateTime.now().toLocalDateTime().toDate();
        final Date actual = dateService.getCurrentDate();
        final long diffTime = actual.getTime() - expected.getTime();

        assertTrue(diffTime<twoMinutes);
    }

    @Test
    public void testAddDays() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.FEBRUARY, 27);
        Date date = dateService.addDays(calendar.getTime(), 5);
        calendar.set(2015, Calendar.MARCH, 4);
        assertTrue(calendar.getTime().equals(date));

        calendar.set(2016, Calendar.FEBRUARY, 27);
        date = dateService.addDays(calendar.getTime(), 5);
        calendar.set(2016, Calendar.MARCH, 3);
        assertTrue(calendar.getTime().equals(date));
    }

    @Test
    public void testGetMothPeriod() throws Exception {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();


        start.set(2015, Calendar.FEBRUARY, 27);
        end.set(2015, Calendar.FEBRUARY, 28);

        assertEquals(dateService.getMothPeriod(start.getTime(), end.getTime()), 1);
        assertEquals(dateService.getMothPeriod(end.getTime(), start.getTime()), 1);

        start.set(2015, Calendar.DECEMBER, 16);
        end.set(2016, Calendar.JANUARY, 15);
        assertEquals(dateService.getMothPeriod(start.getTime(), end.getTime()), 1);

        start.set(2015, Calendar.JUNE, 16);
        end.set(2015, Calendar.JULY, 18);
        assertEquals(dateService.getMothPeriod(start.getTime(), end.getTime()), 1);

        start.set(2015, Calendar.JUNE, 16);
        end.set(2015, Calendar.AUGUST, 1);
        assertEquals(dateService.getMothPeriod(start.getTime(), end.getTime()), 2);

        start.set(2015, Calendar.JUNE, 16);
        end.set(2015, Calendar.AUGUST, 30);
        assertEquals(dateService.getMothPeriod(start.getTime(), end.getTime()), 2);
    }
}