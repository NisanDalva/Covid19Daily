package com.covid19Daily;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
     * Check and parse a string that presents a date and convert it to {@code Date} object
     * @param date - A simple date representing in string format
     * @return {@code Date} - The same date as an object
     * @throws ParseException - if a date is incorrect or in incorrect format
     */
    public Date parseDate(String date) throws ParseException {
        return formatter.parse(date);
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
    
    /**
     * 
     * @param date - A simple date
     * @return {@code Date} - The next day 
     */
    private Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        
        return calendar.getTime();
    }

    /**
     * Get all dates between two different dates
     * @param from - The Start date
     * @param to - The end date INCLUDING
     * @return {@code ArrayList<String>} contains all the dates in format that fits to {@code formater} object
     */
    public ArrayList<String> getDatesRange(Date from, Date to) {
        SimpleDateFormat apiFormatter = new SimpleDateFormat("yyyy-MM-dd");

        ArrayList<String> rv = new ArrayList<>();
        to = getNextDay(to);
        
        while (from.before(to)) {
            rv.add(apiFormatter.format(from));
            from = getNextDay(from);
        }

        return rv;
    }


}
