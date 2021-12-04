package com.covid19Daily.controller;

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
    
}
