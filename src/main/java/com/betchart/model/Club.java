package com.betchart.model;

public class Club {

    private Integer clubId;
    private String countryId;
    private String name;
    private String url;

    //Aggregate data
    private Integer matchesPlayed;
    private Integer goalScored;
    private Integer goalReceived;

    public Club(String countryId, String name, String url) {
        this.countryId = countryId;
        this.name = name;
        this.url = url;
    }

    public Club(Integer clubId, String countryId, String name, String url) {
        this(countryId, name, url);
        this.clubId = clubId;
    }

    /*public Club(Integer clubId, String countryId, String name, String url,
                Integer matchesPlayed, Integer goalScored, Integer goalReceived) {
        this.clubId = clubId;
        this.countryId = countryId;
        this.name = name;
        this.url = url;
        this.matchesPlayed = matchesPlayed;
        this.goalScored = goalScored;
        this.goalReceived = goalReceived;
    }*/

    public Integer getClubId() {
        return clubId;
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

    public Integer getMatchesPlayed() {
        return matchesPlayed;
    }

    public Integer getGoalScored() {
        return goalScored;
    }

    public Integer getGoalReceived() {
        return goalReceived;
    }
}
