package com.covid19Daily.logic;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.covid19Daily.Utils;
import com.covid19Daily.boundaries.AllDetailsBoundary;
import com.covid19Daily.boundaries.HistoryBoundary;
import com.covid19Daily.exceptions.InvalidCountryException;
import com.covid19Daily.exceptions.InvalidDateException;
import com.covid19Daily.exceptions.RangeDateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConfirmedLogicImplementation implements ConfirmedLogic {
    
    private final String ROOT_URL = "https://covid-api.mmediagroup.fr/v1";
    
    private RestTemplate restTemplate;
    private Utils utils;

    public ConfirmedLogicImplementation() {
        this.restTemplate = new RestTemplate();
    }

	@Autowired
	public void setUtils(Utils utils) {
		this.utils = utils;
	}


    public Integer getDailyConfirmedCasesByCountry(String country, String date) {
        AllDetailsBoundary info = getInfoByCountry(country);
        if (info == null)
        // if entered an invalid country, then return status 400 BAD REQUEST
            throw new InvalidCountryException("Enter a valid country, got: \'" + country + "\'");
        
        Date givenDate = null;
        try {
            givenDate = utils.parseInputDate(date);
        } catch (ParseException e) {
            // return status 400 BAD REQUEST
            throw new InvalidDateException("Enter a valid date in format \'" + utils.getINPUT_DATE_FORMAT() + "\'', got: \'" + date + "\'");
        }

        Date yesterday = utils.getPreviousDay(givenDate);

        Integer confirmedGivenDay = info.getDates().get(utils.getApiFormatter().format(givenDate));
        Integer confirmedYesterday = info.getDates().get(utils.getApiFormatter().format(yesterday));

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
        // get all the information we need for both countries
        AllDetailsBoundary sourceInfo = getInfoByCountry(sourceCountry);
        AllDetailsBoundary targetInfo = getInfoByCountry(targetCountry);
        
        if (sourceInfo == null || targetInfo == null)
            // if entered an invalid country, then return status 400 BAD REQUEST
            throw new InvalidCountryException("Enter a valid country, got: \'" + sourceCountry + "\' and \'" + targetCountry + "\'");
        
        Date fromDate = null;
        Date toDate = null;
        
        try {
            fromDate = utils.parseInputDate(from);
            toDate = utils.parseInputDate(to);
        } catch (ParseException e) {
            // if entered an invalid date, then return status 400 BAD REQUEST
            throw new InvalidDateException("Enter a valid date in format \'" + utils.getINPUT_DATE_FORMAT()
                                            + "\'', got: \'" + from + "\' and \'" + to + "\'");
        }
        
        if (fromDate.after(toDate))
        // if entered an invalid range, then return status 400 BAD REQUEST
            throw new RangeDateException("\'from\'' date can\'t be after \'to\' date, got: "
                                            + "from: \'" + from + "\', to: \'" + to + "\'");
        
        // population for both countries
        Double sourcePopulation = sourceInfo.getPopulation().doubleValue();
        Double targetPopulation = targetInfo.getPopulation().doubleValue();
        
        // all dates for calculation
        List<String> allDates = utils.getDatesRange(fromDate, toDate);
        
        ArrayList<Double> rv = allDates.stream()
                            .map((date) -> {
                                try {
                                    // get the cumulative confirmed cases for both countries that fits to each date
                                    Double sourceCumulative = sourceInfo.getDates().get(date).doubleValue();
                                    Double targetCumulative = targetInfo.getDates().get(date).doubleValue();

                                    // the actual comparison between the countries
                                    return (sourceCumulative / sourcePopulation) - (targetCumulative / targetPopulation);
                                } catch (NullPointerException e) {
                                    // just in case at least one of the dates is missing from the API
                                    return null;
                                }
                            })
                            .collect(Collectors.toCollection(ArrayList::new));

        return rv;
    }

    /**
     * Get limited information by given country
     * @param country
     * @return {@code AllDetailsBoundary} - Only contains the population and all the dates available in the API,
     * if the given country is invalid country, then return {@code null}
     */
    private AllDetailsBoundary getInfoByCountry(String country) {
        String query = ROOT_URL + "/history?status=confirmed&country=" + country;

        HistoryBoundary response = restTemplate.getForObject(query, HistoryBoundary.class);
        return response.getAll(); // can be null if the country is invalid
    }
    
}
