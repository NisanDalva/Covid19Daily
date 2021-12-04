package com.covid19Daily.logic;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.covid19Daily.Utils;
import com.covid19Daily.boundaries.AllDetailsBoundary;
import com.covid19Daily.boundaries.HistoryBoundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConfirmedLogicImplementation {
    
    private final String ROOT_URL = "https://covid-api.mmediagroup.fr/v1";
    private final String API_DATE_FORMAT = "yyyy-MM-dd";
    
    SimpleDateFormat formatter;
    private RestTemplate restTemplate;
    private Utils utils;

    public ConfirmedLogicImplementation() {
        this.formatter = new SimpleDateFormat(API_DATE_FORMAT);
        this.restTemplate = new RestTemplate();
    }

    @Autowired
	public void setUtils(Utils utils) {
		this.utils = utils;
	}


    public Integer getDailyConfirmedCasesByCountry(String country, String date) {
        String query = ROOT_URL + "/history?status=confirmed&country=" + country; //TODO handle invalid country
        
        Date givenDate = utils.parseDate(date);

        if(givenDate == null) // invalid date
            throw new RuntimeException(); //TODO handle http status

        Date yesterday = utils.getPreviousDay(givenDate);

        HistoryBoundary response = restTemplate.getForObject(query, HistoryBoundary.class);
        AllDetailsBoundary all = response.getAll();

        Integer confirmedGivenDay = all.getDates().get(formatter.format(givenDate));
        Integer confirmedYesterday = all.getDates().get(formatter.format(yesterday));

        // the normal result, both dates are available in the database
        // since the API gives the cumulative confirmed cases, calculate the relative between the previous day
        if(confirmedGivenDay != null && confirmedYesterday != null)
            return confirmedGivenDay - confirmedYesterday;
        
        // in case the given date is the earliest in the database,
        // so no need to calculate the relative change in confirmed cases
        if(confirmedGivenDay != null && confirmedYesterday == null)
            return confirmedGivenDay;

        // the given date is not in the database at all
        return 0;
    }
}
