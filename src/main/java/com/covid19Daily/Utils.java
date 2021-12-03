package com.covid19Daily;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Utils {
    
    private final String INPUT_DATE_FORMAT = "dd-MM-yyyy";
    SimpleDateFormat formatter;

    @Autowired
    public Utils() {
        formatter = new SimpleDateFormat(INPUT_DATE_FORMAT);
        formatter.setLenient(false);
    }


    /**
     * Parse a string presents a date and convert it to {@code Date} object
     * @param date - A simple date representing in string format
     * @return {@code Date} - If a given date is a correct date and in a correct
     * format, otherwise - {@code null}
     */
    public Date parseDate(String date) {
        Date dateObj;

        try {
            dateObj = formatter.parse(date);
        } catch (ParseException e) {
            dateObj = null;
        }

        return dateObj;
    }

    /**
     * 
     * @param date- A simple date
     * @return {@code Date} - The previous day 
     */
    public Date getPreviousDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        
        return calendar.getTime();
    }
}
