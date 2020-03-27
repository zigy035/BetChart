package com.betchart.model;

import java.math.BigDecimal;
import java.util.Date;

public class Game {

    /*
    GAME_ID
    SEASON_ID
    LEAGUE_ID
    HOME_CLUB_ID
    AWAY_CLUB_ID
    PLAY_DATE
    HOME_GOALS_FT
    AWAY_GOALS_FT
    HOME_GOALS_HT
    AWAY_GOALS_HT

    ODD_1
    ODD_X
    ODD_2
     */

    private Integer gameId;
    private String seasonId;
    private String leagueId;
    private Integer homeClubId;
    private Integer awayClubId;
    private Date playDate;

    private Integer homeGoalsFt;
    private Integer awayGoalsFt;
    private Integer homeGoalsHt;
    private Integer awayGoalsHt;

    private Integer homeShots;
    private Integer awayShots;
    private Integer homeShotsOt;
    private Integer awayShotsOt;

    private Integer homeFouls;
    private Integer awayFouls;

    private BigDecimal odd1;
    private BigDecimal oddX;
    private BigDecimal odd2;

    private Boolean awarded;

    public Game(String seasonId, String leagueId, Integer homeClubId, Integer awayClubId,
                Date playDate, Integer homeGoalsFt, Integer awayGoalsFt,
                Integer homeGoalsHt, Integer awayGoalsHt, Integer homeShots, Integer awayShots,
                Integer homeShotsOt, Integer awayShotsOt, Integer homeFouls, Integer awayFouls,
                BigDecimal odd1, BigDecimal oddX, BigDecimal odd2, Boolean awarded) {

        this.seasonId = seasonId;
        this.leagueId = leagueId;
        this.homeClubId = homeClubId;
        this.awayClubId = awayClubId;
        this.playDate = playDate;
        this.homeGoalsFt = homeGoalsFt;
        this.awayGoalsFt = awayGoalsFt;
        this.homeGoalsHt = homeGoalsHt;
        this.awayGoalsHt = awayGoalsHt;
        this.homeShots = homeShots;
        this.awayShots = awayShots;
        this.homeShotsOt = homeShotsOt;
        this.awayShotsOt = awayShotsOt;
        this.homeFouls = homeFouls;
        this.awayFouls = awayFouls;
        this.odd1 = odd1;
        this.oddX = oddX;
        this.odd2 = odd2;
        this.awarded = awarded;
    }

    public Game(Integer gameId, String seasonId, String leagueId, Integer homeClubId, Integer awayClubId,
                Date playDate, Integer homeGoalsFt, Integer awayGoalsFt, Integer homeGoalsHt,
                Integer awayGoalsHt, Integer homeShots, Integer awayShots, Integer homeShotsOt,
                Integer awayShotsOt, Integer homeFouls, Integer awayFouls,
                BigDecimal odd1, BigDecimal oddX, BigDecimal odd2, Boolean awarded) {

        this(seasonId, leagueId, homeClubId, awayClubId, playDate, homeGoalsFt, awayGoalsFt,
                homeGoalsHt, awayGoalsHt, homeShots, awayShots, homeShotsOt, awayShotsOt,
                homeFouls, awayFouls, odd1, oddX, odd2, awarded);
        this.gameId = gameId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getSeasonId() {
        return seasonId;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public Integer getHomeClubId() {
        return homeClubId;
    }

    public Integer getAwayClubId() {
        return awayClubId;
    }

    public Date getPlayDate() {
        return playDate;
    }

    public Integer getHomeGoalsFt() {
        return homeGoalsFt;
    }

    public Integer getAwayGoalsFt() {
        return awayGoalsFt;
    }

    public Integer getHomeGoalsHt() {
        return homeGoalsHt;
    }

    public Integer getAwayGoalsHt() {
        return awayGoalsHt;
    }

    public Integer getHomeShots() {
        return homeShots;
    }

    public Integer getAwayShots() {
        return awayShots;
    }

    public Integer getHomeShotsOt() {
        return homeShotsOt;
    }

    public Integer getAwayShotsOt() {
        return awayShotsOt;
    }

    public Integer getHomeFouls() {
        return homeFouls;
    }

    public Integer getAwayFouls() {
        return awayFouls;
    }

    public BigDecimal getOdd1() {
        return odd1;
    }

    public BigDecimal getOddX() {
        return oddX;
    }

    public BigDecimal getOdd2() {
        return odd2;
    }

    public Boolean getAwarded() {
        return awarded;
    }

    //toString...
}
