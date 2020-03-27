package com.betchart.service.impl;

import com.betchart.dao.GameDAO;
import com.betchart.model.Game;
import com.betchart.model.GameGoalsEst;
import com.betchart.model.GameOdds;
import com.betchart.model.GameResultEst;
import com.betchart.service.GameService;
import com.betchart.util.BigDecimalOps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class GameServiceImpl implements GameService {

    //TODO: configure this in app.properties
    private static final BigDecimal minOdd = new BigDecimal(1.7);
    private static final BigDecimal maxBothSurIndex = new BigDecimal(3);
    private static final BigDecimal maxSepSurIndex = new BigDecimal(5);

    private static final String currentSeason = "2019-2020";

    private GameDAO gameDAO;

    public GameServiceImpl(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    /*
    for current season
    - (calculate???) - add advantageCounter

    for other seasons:
    -check tradition for these teams in the past - add advantageCounter-

     */

    @Override
    public GameResultEst getResultEstimation(Game game) {

        String leagueId = game.getLeagueId();
        Integer homeClubId = game.getHomeClubId();
        Integer awayClubId = game.getAwayClubId();
        //check odds for these teams in the past
        // (remove this match if odds are not found or
        // or odd is less that min configured)
        GameOdds gameOdds = gameDAO.getApproxGameOdds(game);
        if (gameOdds == null || gameOdds.getMinOdd().compareTo(minOdd) < 0) {
            return null;
        }

        //Check positive & negative surprise index (sum)
        //1.2   5.0   10.0
        //1.8   3.2   4.5
        //2.4   3.0   2.6
        // (remove this match if (for any team)
        //      * positive and negative indexes are gt 3
        //      * positive index is gt 10
        //      * negative index is gt 10
        BigDecimal homePosSurIndex =
                gameDAO.getPositiveSurpriseIndex(currentSeason, leagueId, homeClubId);
        BigDecimal homeNegSurIndex =
                gameDAO.getNegativeSurpriseIndex(currentSeason, leagueId, homeClubId);

        BigDecimal awayPosSurIndex =
                gameDAO.getPositiveSurpriseIndex(currentSeason, leagueId, awayClubId);
        BigDecimal awayNegSurIndex =
                gameDAO.getNegativeSurpriseIndex(currentSeason, leagueId, awayClubId);

        if (homePosSurIndex.compareTo(maxBothSurIndex) > 0 || homeNegSurIndex.compareTo(maxBothSurIndex) > 0) {
            return null;
        }

        if (awayPosSurIndex.compareTo(maxBothSurIndex) > 0 || awayNegSurIndex.compareTo(maxBothSurIndex) > 0) {
            return null;
        }

        if (homePosSurIndex.compareTo(maxSepSurIndex) > 0 || awayPosSurIndex.compareTo(maxSepSurIndex) > 0 ||
                homeNegSurIndex.compareTo(maxSepSurIndex) > 0 || awayNegSurIndex.compareTo(maxSepSurIndex) > 0) {
            return null;
        }

        //Calculate avg team shots and calculate ratio:
        BigDecimal avgHs = gameDAO.getAverageShots(currentSeason, leagueId, homeClubId);
        BigDecimal avgAs = gameDAO.getAverageShots(currentSeason, leagueId, awayClubId);
        BigDecimal avgHsRatio = BigDecimalOps.divide(avgHs, BigDecimalOps.sum(avgHs, avgAs));
        BigDecimal avgAsRatio = BigDecimalOps.divide(avgAs, BigDecimalOps.sum(avgHs, avgAs));

        //Calculate avg team shots on target and calculate ratio:
        BigDecimal avgHst = gameDAO.getAverageShotsOnTarget(currentSeason, leagueId, homeClubId);
        BigDecimal avgAst = gameDAO.getAverageShotsOnTarget(currentSeason, leagueId, awayClubId);
        BigDecimal avgHstRatio = BigDecimalOps.divide(avgHst, BigDecimalOps.sum(avgHst, avgAst));
        BigDecimal avgAstRatio = BigDecimalOps.divide(avgAst, BigDecimalOps.sum(avgHst, avgAst));

        //Calculate shot precision: 
        BigDecimal avgHsp = BigDecimalOps.divide(avgHst, avgHs);
        BigDecimal avgAsp = BigDecimalOps.divide(avgAst, avgAs);

        //Calculate shot efficiency:
        BigDecimal totalHs = gameDAO.getTotalShots(currentSeason, leagueId, homeClubId);
        BigDecimal totalAs = gameDAO.getTotalShots(currentSeason, leagueId, awayClubId);
        BigDecimal totalHgs = gameDAO.getTotalGoalsScored(currentSeason, leagueId, homeClubId);
        BigDecimal totalAgs = gameDAO.getTotalGoalsScored(currentSeason, leagueId, awayClubId);
        BigDecimal avgHsEff = BigDecimalOps.divide(totalHgs, totalHs);
        BigDecimal avgAsEff = BigDecimalOps.divide(totalAgs, totalAs);

        //Calculate offence efficiency:
        BigDecimal avgHoEff = BigDecimalOps.divide(totalHgs, BigDecimalOps.sum(totalHgs, totalAgs));
        BigDecimal avgAoEff = BigDecimalOps.divide(totalAgs, BigDecimalOps.sum(totalHgs, totalAgs));

        //Calculate defence failures:
        BigDecimal totalHgr = gameDAO.getTotalGoalsReceived(currentSeason, leagueId, homeClubId);
        BigDecimal totalAgr = gameDAO.getTotalGoalsReceived(currentSeason, leagueId, awayClubId);
        BigDecimal avgHdFail = BigDecimalOps.divide(totalHgr, BigDecimalOps.sum(totalHgr, totalAgr));
        BigDecimal avgAdFail = BigDecimalOps.divide(totalAgr, BigDecimalOps.sum(totalHgr, totalAgr));

        //Check tradition for these teams in the past
        Integer homeWins = gameDAO.getHomeWins(currentSeason, leagueId, homeClubId, awayClubId);
        Integer awayWins = gameDAO.getAwayWins(currentSeason, leagueId, homeClubId, awayClubId);
        BigDecimal homeWinsDec = new BigDecimal(homeWins);
        BigDecimal awayWinsDec = new BigDecimal(awayWins);
        BigDecimal homeWinRatio = BigDecimalOps.divide(homeWinsDec, BigDecimalOps.sum(homeWinsDec, awayWinsDec));
        BigDecimal awayWinRatio = BigDecimalOps.divide(awayWinsDec, BigDecimalOps.sum(homeWinsDec, awayWinsDec));

        //Calculate totalPts for both teams
        BigDecimal totalHomePts = BigDecimalOps.substract(BigDecimalOps.sum(
                avgHsRatio, avgHstRatio, avgHsp, avgHsEff, homeWinRatio, avgHoEff), avgHdFail);
        BigDecimal totalAwayPts = BigDecimalOps.substract(BigDecimalOps.sum(
                avgAsRatio, avgAstRatio, avgAsp, avgAsEff, awayWinRatio, avgAoEff), avgAdFail);

        //Key player(s) missing (injury or card) advantage
        // totalPts*(14-N)/14   - N is a number of key missing player
        int totalHomeKeyMissPlayers = 0;
        int totalAwayKeyMissPlayers = 0;
        BigDecimal totalPlayers = new BigDecimal(14);
        BigDecimal homePlayers = new BigDecimal(14-totalHomeKeyMissPlayers);
        BigDecimal awayPlayers = new BigDecimal(14-totalAwayKeyMissPlayers);
        totalHomePts = BigDecimalOps.multiply(totalHomePts, BigDecimalOps.divide(homePlayers, totalPlayers));
        totalAwayPts = BigDecimalOps.multiply(totalAwayPts, BigDecimalOps.divide(awayPlayers, totalPlayers));

        /*return DTO */
        return null;
    }

    @Override
    public GameGoalsEst getGoalsEstimation(Game game) {
        return null;
    }

}
