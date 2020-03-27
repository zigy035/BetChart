package com.betchart.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {

    public static Date getNextDayInWeek(int day, int week) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        c.add(Calendar.DAY_OF_MONTH, 7*week);

        return c.getTime();
    }

    public static String getDateAsString(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public static void main(String[] args) {
        System.out.println(getNextDayInWeek(6,0));
        System.out.println(getNextDayInWeek(1,1));
    }
}
