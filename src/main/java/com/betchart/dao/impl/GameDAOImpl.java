package com.betchart.dao.impl;

import com.betchart.dao.GameDAO;
import com.betchart.model.Game;
import com.betchart.model.GameOdds;
import com.betchart.model.mapper.GameMapper;
import com.betchart.model.mapper.GameOddsMapper;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class GameDAOImpl extends JdbcBaseDAO implements GameDAO {

    @Override
    public Game getGame(String seasonId, String leagueId, Integer homeClubId, Integer awayClubId) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM GAME " +
                            "WHERE SEASON_ID = ? AND LEAGUE_ID = ? AND HOME_CLUB_ID = ? AND AWAY_CLUB_ID = ?",
                    new Object[] {seasonId, leagueId, homeClubId, awayClubId},
                    new GameMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Game> getGames(String startDate, String endDate) {
        return getJdbcTemplate().query("SELECT * FROM GAME " +
                "WHERE date > ? AND date < ?", new Object[] {startDate, endDate},
                new GameMapper());
    }

    @Override
    public void create(Game game) {
        getJdbcTemplate().update("INSERT INTO GAME " +
                "(SEASON_ID, LEAGUE_ID, HOME_CLUB_ID, AWAY_CLUB_ID, PLAY_DATE, " +
                "HOME_GOALS_FT, AWAY_GOALS_FT, HOME_GOALS_HT, AWAY_GOALS_HT, " +
                "HOME_SHOTS, AWAY_SHOTS, HOME_SHOTS_OT, AWAY_SHOTS_OT, HOME_FOULS, AWAY_FOULS, " +
                "ODD_1, ODD_X, ODD_2, IS_AWARDED) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                game.getSeasonId(), game.getLeagueId(), game.getHomeClubId(), game.getAwayClubId(),
                game.getPlayDate(), game.getHomeGoalsFt(), game.getAwayGoalsFt(),
                game.getHomeGoalsHt(), game.getAwayGoalsHt(),
                game.getHomeShots(), game.getAwayShots(), game.getHomeShotsOt(), game.getAwayShotsOt(),
                game.getHomeFouls(), game.getAwayFouls(),
                game.getOdd1(), game.getOddX(), game.getOdd2(),
                BooleanUtils.toInteger(game.getAwarded()));
    }

    @Override
    public GameOdds getApproxGameOdds(Game game) {
        try {
            return getJdbcTemplate().queryForObject("SELECT ODD_1, ODD_X, ODD_2 FROM GAME " +
                    "WHERE LEAGUE_ID = ? AND HOME_CLUB_ID = ? AND AWAY_CLUB_ID = ? " +
                    "ORDER BY PLAY_DATE DESC " +
                    "LIMIT 1",
                    new Object[] {game.getLeagueId(), game.getHomeClubId(), game.getAwayClubId()},
                    new GameOddsMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public BigDecimal getPositiveSurpriseIndex(String seasonId, String leagueId, Integer clubId) {
        try {
            return getJdbcTemplate().queryForObject(
                "SELECT COALESCE(SUM(ABS(G.ODD_1 - G.ODD_2)), 0) " +
                "FROM GAME G " +
                "JOIN CLUB HC ON HC.CLUB_ID = G.HOME_CLUB_ID " +
                "JOIN CLUB AC ON AC.CLUB_ID = G.AWAY_CLUB_ID " +
                "WHERE G.SEASON_ID = ? AND LEAGUE_ID = ? AND (" +
                "(G.ODD_1 > G.ODD_2 AND G.HOME_GOALS_FT > G.AWAY_GOALS_FT AND HC.CLUB_ID = ?) " +
                "OR " +
                "(G.ODD_1 < G.ODD_2 AND G.HOME_GOALS_FT < G.AWAY_GOALS_FT AND AC.CLUB_ID = ?) " +
                ")", new Object[] {seasonId, leagueId, clubId, clubId}, BigDecimal.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public BigDecimal getNegativeSurpriseIndex(String seasonId, String leagueId, Integer clubId) {
        try {
            return getJdbcTemplate().queryForObject(
                "SELECT COALESCE(SUM(ABS(G.ODD_1 - G.ODD_2)), 0) " +
                "FROM GAME G " +
                "JOIN CLUB HC ON HC.CLUB_ID = G.HOME_CLUB_ID " +
                "JOIN CLUB AC ON AC.CLUB_ID = G.AWAY_CLUB_ID " +
                "WHERE G.SEASON_ID = ? AND LEAGUE_ID = ? AND (" +
                "(G.ODD_1 > G.ODD_2 AND G.HOME_GOALS_FT > G.AWAY_GOALS_FT AND AC.CLUB_ID = ?) " +
                "OR " +
                "(G.ODD_1 < G.ODD_2 AND G.HOME_GOALS_FT < G.AWAY_GOALS_FT AND HC.CLUB_ID = ?) " +
                ")", new Object[] {seasonId, leagueId, clubId, clubId}, BigDecimal.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void deleteAll() {
        getJdbcTemplate().update("DELETE FROM GAME");
    }

    @Override
    public void update(Game game) {
        getJdbcTemplate().update("UPDATE GAME SET " +
                "HOME_GOALS_FT = ?, AWAY_GOALS_FT = ?, HOME_GOALS_HT = ?, AWAY_GOALS_HT = ?, " +
                "HOME_SHOTS = ?, AWAY_SHOTS = ?, HOME_SHOTS_OT = ?, AWAY_SHOTS_OT = ?, " +
                "HOME_FOULS = ?, AWAY_FOULS = ?, ODD_1 = ?, ODD_X = ?, ODD_2 = ?, IS_AWARDED = ? " +
                "WHERE GAME_ID = ?", game.getHomeGoalsFt(), game.getAwayGoalsFt(),
                game.getHomeGoalsHt(), game.getAwayGoalsHt(),
                game.getHomeShots(), game.getAwayShots(), game.getHomeShotsOt(), game.getAwayShotsOt(),
                game.getHomeFouls(), game.getAwayFouls(),
                game.getOdd1(), game.getOddX(), game.getOdd2(),
                BooleanUtils.toInteger(game.getAwarded()), game.getGameId());
    }

    @Override
    public BigDecimal getAverageShots(String currentSeason, String leagueId, Integer clubId) {
        try {
            return getJdbcTemplate().queryForObject(
                "SELECT ROUND(AVG(RESULT.SHOTS), 2) AS AVG_SHOTS FROM (" +
                "SELECT CASE " +
                " WHEN G.HOME_CLUB_ID = ? THEN G.HOME_SHOTS " +
                " WHEN G.AWAY_CLUB_ID = ? THEN G.AWAY_SHOTS " +
                "END AS SHOTS " +
                "FROM GAME G " +
                "WHERE G.SEASON_ID = ? AND LEAGUE_ID = ? " +
                "AND (G.HOME_CLUB_ID = ? OR G.AWAY_CLUB_ID = ?) " +
                "ORDER BY G.PLAY_DATE) RESULT",
                new Object[] {clubId, clubId, currentSeason, leagueId, clubId, clubId},
                    BigDecimal.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public BigDecimal getAverageShotsOnTarget(String currentSeason, String leagueId, Integer clubId) {
        try {
            return getJdbcTemplate().queryForObject(
                "SELECT ROUND(AVG(RESULT.SHOTS_OT), 2) AS AVG_SHOTS FROM (" +
                "SELECT CASE " +
                " WHEN G.HOME_CLUB_ID = ? THEN G.HOME_SHOTS_OT " +
                " WHEN G.AWAY_CLUB_ID = ? THEN G.AWAY_SHOTS_OT " +
                "END AS SHOTS_OT " +
                "FROM GAME G " +
                "WHERE G.SEASON_ID = ? AND LEAGUE_ID = ? " +
                "AND (G.HOME_CLUB_ID = ? OR G.AWAY_CLUB_ID = ?) " +
                "ORDER BY G.PLAY_DATE) RESULT",
                new Object[] {clubId, clubId, currentSeason, leagueId, clubId, clubId},
                BigDecimal.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public BigDecimal getTotalShots(String currentSeason, String leagueId, Integer clubId) {
        try {
            return getJdbcTemplate().queryForObject(
                "SELECT ROUND(SUM(RESULT.SHOTS), 2) AS SUM_SHOTS FROM (" +
                "SELECT CASE " +
                " WHEN G.HOME_CLUB_ID = ? THEN G.HOME_SHOTS " +
                " WHEN G.AWAY_CLUB_ID = ? THEN G.AWAY_SHOTS " +
                "END AS SHOTS " +
                "FROM GAME G " +
                "WHERE G.SEASON_ID = ? AND LEAGUE_ID = ? " +
                "AND (G.HOME_CLUB_ID = ? OR G.AWAY_CLUB_ID = ?) " +
                "ORDER BY G.PLAY_DATE) RESULT",
                new Object[] {clubId, clubId, currentSeason, leagueId, clubId, clubId},
                BigDecimal.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public BigDecimal getTotalGoalsScored(String currentSeason, String leagueId, Integer clubId) {
        try {
            return getJdbcTemplate().queryForObject(
                "SELECT ROUND(SUM(RESULT.GOALS), 2) AS SUM_GOALS FROM (" +
                "SELECT CASE " +
                " WHEN G.HOME_CLUB_ID = ? THEN G.HOME_GOALS_FT " +
                " WHEN G.AWAY_CLUB_ID = ? THEN G.AWAY_GOALS_FT" +
                "END AS GOALS " +
                "FROM GAME G " +
                "WHERE G.SEASON_ID = ? AND LEAGUE_ID = ? " +
                "AND (G.HOME_CLUB_ID = ? OR G.AWAY_CLUB_ID = ?) " +
                "ORDER BY G.PLAY_DATE) RESULT",
                new Object[] {clubId, clubId, currentSeason, leagueId, clubId, clubId},
                BigDecimal.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public BigDecimal getTotalGoalsReceived(String currentSeason, String leagueId, Integer clubId) {
        try {
            return getJdbcTemplate().queryForObject(
                "SELECT ROUND(SUM(RESULT.GOALS), 2) AS SUM_GOALS FROM (" +
                "SELECT CASE " +
                " WHEN G.HOME_CLUB_ID = ? THEN G.AWAY_GOALS_FT " +
                " WHEN G.AWAY_CLUB_ID = ? THEN G.HOME_GOALS_FT " +
                "END AS GOALS " +
                "FROM GAME G " +
                "WHERE G.SEASON_ID = ? AND LEAGUE_ID = ? " +
                "AND (G.HOME_CLUB_ID = ? OR G.AWAY_CLUB_ID = ?) " +
                "ORDER BY G.PLAY_DATE) RESULT",
                new Object[] {clubId, clubId, currentSeason, leagueId, clubId, clubId},
                BigDecimal.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer getHomeWins(String currentSeason, String leagueId, Integer homeClubId, Integer awayClubId) {
        try {
            return getJdbcTemplate().queryForObject(
                "SELECT COUNT(*) " +
                "FROM GAME G " +
                "JOIN CLUB HC ON HC.CLUB_ID = G.HOME_CLUB_ID " +
                "JOIN CLUB AC ON AC.CLUB_ID = G.AWAY_CLUB_ID " +
                "WHERE LEAGUE_ID = ? AND " +
                "((G.HOME_CLUB_ID = ? AND G.AWAY_CLUB_ID = ? AND G.HOME_GOALS_FT > G.AWAY_GOALS_FT) " +
                "OR " +
                "(G.HOME_CLUB_ID = ? AND G.AWAY_CLUB_ID = ? AND G.HOME_GOALS_FT < G.AWAY_GOALS_FT))",
                new Object[] {leagueId, homeClubId, awayClubId, awayClubId, homeClubId},
                Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer getAwayWins(String currentSeason, String leagueId, Integer homeClubId, Integer awayClubId) {
        try {
            return getJdbcTemplate().queryForObject(
                "SELECT COUNT(*) " +
                "FROM GAME G " +
                "JOIN CLUB HC ON HC.CLUB_ID = G.HOME_CLUB_ID " +
                "JOIN CLUB AC ON AC.CLUB_ID = G.AWAY_CLUB_ID " +
                "WHERE LEAGUE_ID = ? AND " +
                "((G.HOME_CLUB_ID = ? AND G.AWAY_CLUB_ID = ? AND G.HOME_GOALS_FT > G.AWAY_GOALS_FT) " +
                "OR " +
                "(G.HOME_CLUB_ID = ? AND G.AWAY_CLUB_ID = ? AND G.HOME_GOALS_FT < G.AWAY_GOALS_FT))",
                new Object[] {leagueId, awayClubId, homeClubId, homeClubId, awayClubId},
                Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
