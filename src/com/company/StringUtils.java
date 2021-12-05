package com.company;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringUtils {
    //Used to set time to 0 for the Date Object
    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    //Used to convert String date to a valid Date Object
    public static Date convert(String date)throws Exception {
        System.out.println(date);
        return new SimpleDateFormat("dd/MM/yyyy").parse(date);
    }
}



