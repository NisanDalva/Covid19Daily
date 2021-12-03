package com.covid19Daily.boundaries;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryBoundary {
    
    @JsonProperty(value="All")
    private AllDetailsBoundary All;

    public HistoryBoundary() {
    }

    public AllDetailsBoundary getAll() {
        return this.All;
    }

    public void setAll(AllDetailsBoundary all) {
        this.All = all;
    }

    @Override
    public String toString() {
        return "HistoryBoundary [All=" + All + "]";
    }

}
