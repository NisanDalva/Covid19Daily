package com.covid19Daily.boundaries;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AllDetailsBoundary {
    
    private int population;
    private Map<String, Integer> dates;


    public AllDetailsBoundary() {
    }


    public int getPopulation() {
        return this.population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public Map<String,Integer> getDates() {
        return this.dates;
    }

    public void setDates(Map<String,Integer> dates) {
        this.dates = dates;
    }

}