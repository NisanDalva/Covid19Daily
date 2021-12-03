package com.covid19Daily.logic;


import org.springframework.stereotype.Service;


@Service
public class ConfirmedLogicImplementation {
    
    private final String ROOT_URL = "https://covid-api.mmediagroup.fr/v1";
    private final String API_DATE_FORMAT = "yyyy-MM-dd";
    


    public Integer getDailyConfirmedCasesByCountry(String country, String date) {
        // STUB implementantion
        System.out.println("country = " + country + ", date = " + date);
        return 0;
    }
}
