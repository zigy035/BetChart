package com.betchart.model;

public class League {

    private String leagueId;
    private String countryId;
    private String name;
    private String url;

    public League(String leagueId, String countryId, String name, String url) {
        this.leagueId = leagueId;
        this.countryId = countryId;
        this.name = name;
        this.url = url;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public String getCountryId() {
        return countryId;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
