package com.covid19Daily.boundaries;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AllDetailsBoundary {
    
    private Integer population;
    private Map<String, Integer> dates;


    public AllDetailsBoundary() {
    }

    public Integer getPopulation() {
        return this.population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Map<String,Integer> getDates() {
        return this.dates;
    }

    public void setDates(Map<String,Integer> dates) {
        this.dates = dates;
    }

    @Override
    public String toString() {
        return "AllDetailsBoundary [dates=" + dates + ", population=" + population + "]";
    }

}
