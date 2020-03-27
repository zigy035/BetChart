package com.betchart.model;

public class Season {

    private String seasonId;
    private String name;
    private Integer startYear;
    private Integer endYear;
    private String csvUrl;

    public Season(String seasonId, String name, Integer startYear, Integer endYear, String csvUrl) {
        this.seasonId = seasonId;
        this.name = name;
        this.startYear = startYear;
        this.endYear = endYear;
        this.csvUrl = csvUrl;
    }

    public String getSeasonId() {
        return seasonId;
    }

    public String getName() {
        return name;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public String getCsvUrl() {
        return csvUrl;
    }
}
