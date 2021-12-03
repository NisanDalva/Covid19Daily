package com.covid19Daily.boundaries;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryBoundary {
    
    private AllDetailsBoundary all;


    public HistoryBoundary() {
    }

    public AllDetailsBoundary getAll() {
        return this.all;
    }

    public void setAll(AllDetailsBoundary all) {
        this.all = all;
    }

}
