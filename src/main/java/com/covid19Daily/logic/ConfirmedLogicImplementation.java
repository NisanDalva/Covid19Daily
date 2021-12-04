package com.covid19Daily.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.covid19Daily.Utils;
import com.covid19Daily.boundaries.AllDetailsBoundary;
import com.covid19Daily.boundaries.HistoryBoundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConfirmedLogicImplementation implements ConfirmedLogic {
    
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
        Date givenDate = null;
        
        try {
            givenDate = utils.parseDate(date);
        } catch (ParseException e) {
            throw new RuntimeException();
        }

        Date yesterday = utils.getPreviousDay(givenDate);

        AllDetailsBoundary info = getInfoByCountry(country);

        Integer confirmedGivenDay = info.getDates().get(formatter.format(givenDate));
        Integer confirmedYesterday = info.getDates().get(formatter.format(yesterday));

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


    public ArrayList<Double> compareCountries(String sourceCountry, String targetCountry, String from, String to) {
        Date fromDate = null;
        Date toDate = null;
        
        try {
            fromDate = utils.parseDate(from);
            toDate = utils.parseDate(to);
        } catch (ParseException e) {
            throw new RuntimeException();
        }
        
        if (fromDate.after(toDate))
            throw new RuntimeException(); //TODO handle http status
        
        AllDetailsBoundary sourceInfo = getInfoByCountry(sourceCountry);
        AllDetailsBoundary targetInfo = getInfoByCountry(targetCountry);
        
        Double sourcePopulation = sourceInfo.getPopulation().doubleValue();
        Double targetPopulation = targetInfo.getPopulation().doubleValue();

        System.out.println("sourcePopulation = " + sourcePopulation);
        System.out.println("targetPopulation = " + targetPopulation);
        
        ArrayList<String> allDates = utils.getDatesRange(fromDate, toDate);
        ArrayList<Double> rv = new ArrayList<>();
        
        for (String date: allDates) {
            try {
                Double sourceCumulative = sourceInfo.getDates().get(date).doubleValue();
                Double targetCumulative = targetInfo.getDates().get(date).doubleValue();
    
                System.out.println("============================");
    
                System.out.println("for date = " + date);
                System.out.println("sourceCumulative = " + sourceCumulative);
                System.out.println("targetCumulative = " + targetCumulative);
    
                System.out.println("============================");
                // the actual comparison between the countries
                rv.add((sourceCumulative / sourcePopulation) - (targetCumulative / targetPopulation));

            } catch (NullPointerException e) {
                // do nothing, just in case at least one of the dates is missing in the database
            }
        }
        


        return rv;
    }

    /**
     * Get limited information by given country
     * @param country
     * @return {@code AllDetailsBoundary} - Only contains the population and all the dates available in the API
     */
    private AllDetailsBoundary getInfoByCountry(String country) {
        String query = ROOT_URL + "/history?status=confirmed&country=" + country; //TODO handle invalid country

        HistoryBoundary response = restTemplate.getForObject(query, HistoryBoundary.class);
        return response.getAll();
    }
}
