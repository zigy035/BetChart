package com.betchart.dto;

public class GameDTO /*implements Comparable*/ {

    private String homeClub;
    private String awayClub;
    //private Double homeRating;
    //private Double awayRating;
    private Integer homeMissingPlayerCount;
    private Integer awayMissingPlayerCount;

    private Integer homeGoalPts;
    private Integer awayGoalPts;

    private Double homeGoalEst;
    private Double awayGoalEst;

    /*private Integer homeShotsPts;
    private Integer awayShotsPts;

    private Integer homeShotsOTPts;
    private Integer awayShotsOTPts;

    private Integer homeKeyPassesPts;
    private Integer awayKeyPassesPts;

    private Double homePassAccuracyPts;
    private Double awayPassAccuracyPts;

    private Integer homeAerialsWonPts;
    private Integer awayAerialsWonPts;

    private Integer homeTouchesPts;
    private Integer awayTouchesPts;*/

    private Double homeRatingPts;
    private Double awayRatingPts;


    public String getHomeClub() {
        return homeClub;
    }

    public void setHomeClub(String homeClub) {
        this.homeClub = homeClub;
    }

    public String getAwayClub() {
        return awayClub;
    }

    public void setAwayClub(String awayClub) {
        this.awayClub = awayClub;
    }

    /*
    public Double getHomeRating() {
        return homeRating;
    }

    public void setHomeRating(Double homeRating) {
        this.homeRating = homeRating;
    }

    public Double getAwayRating() {
        return awayRating;
    }

    public void setAwayRating(Double awayRating) {
        this.awayRating = awayRating;
    }*/

    public Integer getHomeMissingPlayerCount() {
        return homeMissingPlayerCount;
    }

    public void setHomeMissingPlayerCount(Integer homeMissingPlayerCount) {
        this.homeMissingPlayerCount = homeMissingPlayerCount;
    }

    public Integer getAwayMissingPlayerCount() {
        return awayMissingPlayerCount;
    }

    public void setAwayMissingPlayerCount(Integer awayMissingPlayerCount) {
        this.awayMissingPlayerCount = awayMissingPlayerCount;
    }

    // new get/set methods

    public Integer getHomeGoalPts() {
        return homeGoalPts;
    }

    public void setHomeGoalPts(Integer homeGoalPts) {
        this.homeGoalPts = homeGoalPts;
    }

    public Integer getAwayGoalPts() {
        return awayGoalPts;
    }

    public void setAwayGoalPts(Integer awayGoalPts) {
        this.awayGoalPts = awayGoalPts;
    }

    public Double getHomeGoalEst() {
        return homeGoalEst;
    }

    public void setHomeGoalEst(Double homeGoalEst) {
        this.homeGoalEst = homeGoalEst;
    }

    public Double getAwayGoalEst() {
        return awayGoalEst;
    }

    public void setAwayGoalEst(Double awayGoalEst) {
        this.awayGoalEst = awayGoalEst;
    }

    /*
    public Integer getHomeShotsPts() {
        return homeShotsPts;
    }

    public void setHomeShotsPts(Integer homeShotsPts) {
        this.homeShotsPts = homeShotsPts;
    }

    public Integer getAwayShotsPts() {
        return awayShotsPts;
    }

    public void setAwayShotsPts(Integer awayShotsPts) {
        this.awayShotsPts = awayShotsPts;
    }

    public Integer getHomeShotsOTPts() {
        return homeShotsOTPts;
    }

    public void setHomeShotsOTPts(Integer homeShotsOTPts) {
        this.homeShotsOTPts = homeShotsOTPts;
    }

    public Integer getAwayShotsOTPts() {
        return awayShotsOTPts;
    }

    public void setAwayShotsOTPts(Integer awayShotsOTPts) {
        this.awayShotsOTPts = awayShotsOTPts;
    }

    public Integer getHomeKeyPassesPts() {
        return homeKeyPassesPts;
    }

    public void setHomeKeyPassesPts(Integer homeKeyPassesPts) {
        this.homeKeyPassesPts = homeKeyPassesPts;
    }

    public Integer getAwayKeyPassesPts() {
        return awayKeyPassesPts;
    }

    public void setAwayKeyPassesPts(Integer awayKeyPassesPts) {
        this.awayKeyPassesPts = awayKeyPassesPts;
    }

    public Double getHomePassAccuracyPts() {
        return homePassAccuracyPts;
    }

    public void setHomePassAccuracyPts(Double homePassAccuracyPts) {
        this.homePassAccuracyPts = homePassAccuracyPts;
    }

    public Double getAwayPassAccuracyPts() {
        return awayPassAccuracyPts;
    }

    public void setAwayPassAccuracyPts(Double awayPassAccuracyPts) {
        this.awayPassAccuracyPts = awayPassAccuracyPts;
    }

    public Integer getHomeAerialsWonPts() {
        return homeAerialsWonPts;
    }

    public void setHomeAerialsWonPts(Integer homeAerialsWonPts) {
        this.homeAerialsWonPts = homeAerialsWonPts;
    }

    public Integer getAwayAerialsWonPts() {
        return awayAerialsWonPts;
    }

    public void setAwayAerialsWonPts(Integer awayAerialsWonPts) {
        this.awayAerialsWonPts = awayAerialsWonPts;
    }

    public Integer getHomeTouchesPts() {
        return homeTouchesPts;
    }

    public void setHomeTouchesPts(Integer homeTouchesPts) {
        this.homeTouchesPts = homeTouchesPts;
    }

    public Integer getAwayTouchesPts() {
        return awayTouchesPts;
    }

    public void setAwayTouchesPts(Integer awayTouchesPts) {
        this.awayTouchesPts = awayTouchesPts;
    }*/

    public Double getHomeRatingPts() {
        return homeRatingPts;
    }

    public void setHomeRatingPts(Double homeRatingPts) {
        this.homeRatingPts = homeRatingPts;
    }

    public Double getAwayRatingPts() {
        return awayRatingPts;
    }

    public void setAwayRatingPts(Double awayRatingPts) {
        this.awayRatingPts = awayRatingPts;
    }


    /*@Override
    public int compareTo(Object o) {

        GameDTO dto = (GameDTO) o;
        Integer missDiff1 = awayMissingPlayerCount - homeMissingPlayerCount;
        Integer missDiff2 = dto.awayMissingPlayerCount - dto.homeMissingPlayerCount;
        int missComp = missDiff1.compareTo(missDiff2);
        if (missComp != 0) {
            return missComp;
        }

        double ratingDiff1 = homeRating - awayRating;
        double ratingDiff2 = dto.homeRating - dto.awayRating;

        if (ratingDiff1 == ratingDiff2) {
            return 0;
        } else if (ratingDiff1 < ratingDiff2) {
            return 1;
        } else {
            return -1;
        }

    }*/
}
