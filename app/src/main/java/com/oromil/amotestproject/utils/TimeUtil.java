package com.oromil.amotestproject.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Oromil on 27.07.2017.
 */

public class TimeUtil {
    public  static String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        }catch (Exception e) {
        }
        return "";
    }
}
