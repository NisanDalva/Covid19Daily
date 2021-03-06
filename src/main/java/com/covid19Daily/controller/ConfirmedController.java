package com.covid19Daily.controller;

import java.util.List;

import com.covid19Daily.logic.ConfirmedLogicImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/confirmed")
public class ConfirmedController {

    private ConfirmedLogicImplementation confirmedLogicImplementation;

    @Autowired
    public void setConfirmedLogicImplementation(ConfirmedLogicImplementation confirmedLogicImplementation) {
        this.confirmedLogicImplementation = confirmedLogicImplementation;
    }
    
    @RequestMapping(
			path = "/daily",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer getConfirmedCases(
        @RequestParam(name = "country", required = true) String country,
        @RequestParam(name = "date", required = true) String date
    ) {
        return confirmedLogicImplementation.getDailyConfirmedCasesByCountry(country, date);
    }

    @RequestMapping(
			path = "/compare",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Double> compareBetweentwoCountries(
        @RequestParam(name = "sourceCountry", required = true) String sourceCountry,
        @RequestParam(name = "targetCountry", required = true) String targetcountry,
        @RequestParam(name = "from", required = true) String from,
        @RequestParam(name = "to", required = true) String to
    ) {
        return confirmedLogicImplementation.compareCountries(sourceCountry, targetcountry, from, to);
    }
    
}
