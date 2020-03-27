package com.betchart.dao;

import com.betchart.model.Game;
import com.betchart.model.GameOdds;

import java.math.BigDecimal;
import java.util.List;

public interface GameDAO {

    Game getGame(String seasonId, String leagueId, Integer homeClubId, Integer awayClubId);

    List<Game> getGames(String startDate, String endDate);

    void create(Game fscGame);

    GameOdds getApproxGameOdds(Game game);

    /**
     * Positive Surprise Index represents an absolute sum of odd1 and odd2 difference
     * for matches where team made unexpected win. If no such matches the default value is 0.
     *
     * @param seasonId
     * @param leagueId
     * @param clubId
     * @return
     */
    BigDecimal getPositiveSurpriseIndex(String seasonId, String leagueId, Integer clubId);

    /**
     * Negative Surprise Index represents an absolute sum of odd1 and odd2 difference
     * for matches where team suffered unexpected loss. If no such matches the default value is 0.
     *
     * @param seasonId
     * @param leagueId
     * @param clubId
     * @return
     */
    BigDecimal getNegativeSurpriseIndex(String seasonId, String leagueId, Integer clubId);

    void deleteAll();

    void update(Game game);

    BigDecimal getAverageShots(String currentSeason, String leagueId, Integer clubId);

    BigDecimal getAverageShotsOnTarget(String currentSeason, String leagueId, Integer clubId);

    BigDecimal getTotalShots(String currentSeason, String leagueId, Integer clubId);

    BigDecimal getTotalGoalsScored(String currentSeason, String leagueId, Integer clubId);

    BigDecimal getTotalGoalsReceived(String currentSeason, String leagueId, Integer clubId);

    Integer getHomeWins(String currentSeason, String leagueId, Integer homeClubId, Integer awayClubId);

    Integer getAwayWins(String currentSeason, String leagueId, Integer homeClubId, Integer awayClubId);
}
