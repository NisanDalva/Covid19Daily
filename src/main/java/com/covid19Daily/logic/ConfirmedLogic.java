package com.covid19Daily.logic;

import java.util.ArrayList;

public interface ConfirmedLogic {
    
    public Integer getDailyConfirmedCasesByCountry(String country, String date);

    public ArrayList<Double> compareCountries(String sourceCountry, String targetCountry, String from, String to);

}
