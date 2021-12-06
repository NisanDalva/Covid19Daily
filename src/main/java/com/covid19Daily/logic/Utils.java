package com.covid19Daily.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Utils {
    // the format in which the user asks to input dates
    private final String INPUT_DATE_FORMAT = "dd-MM-yyyy";

    // the format the API uses
    private final String API_DATE_FORMAT = "yyyy-MM-dd";

    private SimpleDateFormat inputFormatter;
    private SimpleDateFormat apiFormatter;

    public Utils() {
        inputFormatter = new SimpleDateFormat(INPUT_DATE_FORMAT);
        inputFormatter.setLenient(false);

        apiFormatter = new SimpleDateFormat(API_DATE_FORMAT);
        apiFormatter.setLenient(false);
    }

    public String getINPUT_DATE_FORMAT() {
        return INPUT_DATE_FORMAT;
    }

    public String getAPI_DATE_FORMAT() {
        return API_DATE_FORMAT;
    }

    public SimpleDateFormat getInputFormatter() {
        return inputFormatter;
    }

    public SimpleDateFormat getApiFormatter() {
        return apiFormatter;
    }

    /**
     * Check and parse a string that presents a date and convert it to {@code Date} object
     * @param date - A simple date representing in string format
     * @return {@code Date} - The same date as an object
     * @throws ParseException if a date is incorrect or in incorrect format
     */
    public Date parseInputDate(String date) throws ParseException {
        return inputFormatter.parse(date);
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
     * @return {@code ArrayList<String>} contains all the dates in format that fits to the {@code API} format date
     */
    public ArrayList<String> getDatesRange(Date from, Date to) {
        ArrayList<String> rv = new ArrayList<>();
        to = getNextDay(to);
        
        while (from.before(to)) {
            rv.add(apiFormatter.format(from));
            from = getNextDay(from);
        }

        return rv;
    }

}
