package com.betchart.importer;

import com.betchart.dao.ClubDAO;
import com.betchart.dao.GameDAO;
import com.betchart.dao.LeagueDAO;
import com.betchart.dao.SeasonDAO;
import com.betchart.model.Club;
import com.betchart.model.Game;
import com.betchart.model.League;
import com.betchart.model.Season;
import com.betchart.util.CsvIndex;
import com.betchart.util.LogHelper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@Component
public class CsvGameDataImport {

    private static final Logger LOG = LoggerFactory.getLogger(CsvGameDataImport.class);

    private static final String CURRENT_SEASON = "2019-2020";
    private static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm";
    private static final String DATE_PATTERN = "dd/MM/yy";

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private SeasonDAO seasonDAO;

    @Autowired
    private LeagueDAO leagueDAO;

    @Autowired
    private ClubDAO clubDAO;

    @Autowired
    private GameDAO gameDAO;

    @Value("${csv.base.url}")
    private String csvBaseUrl;

    @Value("${csv.base.dir}")
    private String csvBaseDir;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {

        gameDAO.deleteAll();

        List<Season> seasons = seasonDAO.getSeasons();
        List<League> leagues = leagueDAO.getLeagues();

        for (Season season : seasons) {

            String seasonId = season.getSeasonId();
            long start = System.currentTimeMillis();
            LOG.info("Downloading season {}...", seasonId);

            for (League league : leagues) {

                String leagueId = league.getLeagueId();
                LOG.info("* {} [{}]", league.getName(), leagueId);
                //Download new CSV files
                File csvFile = new File(csvBaseDir + "\\" + seasonId +
                        "\\" + leagueId + ".csv");

                URL csvUrl;
                try {
                    csvUrl = new URL(csvBaseUrl + "/" + season.getCsvUrl() + "/" + leagueId + ".csv");

                    FileUtils.copyURLToFile(csvUrl, csvFile);
                } catch (Exception e) {
                    LOG.error(LogHelper.errorMsg(e));
                }
            }

            long end = System.currentTimeMillis();
            LOG.info("Completed....in {} ms.", (end - start));
        }

        List<String> seasonIDs = seasons.stream()
                .map(Season::getSeasonId)
                .collect(Collectors.toList());

        for (String seasonId : seasonIDs) {

            /*final Resource fileResource = resourceLoader.getResource(CSV_FILE_PATH + "\\" + seasonId);
            String path = fileResource.getFile().getAbsolutePath();
            File dir = new File(CSV_FILE_PATH + "\\" + seasonId);*/

            String dirPath = csvBaseDir + "\\" + seasonId;

            //for current season date has a timestamp and
            // other data are shifted for one column ahead
            DateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
            int csvIndexOffset = 0;
            if (seasonId.equals(CURRENT_SEASON)) {
                dateFormat = new SimpleDateFormat(DATE_TIME_PATTERN);
                csvIndexOffset = 1;
            }
            dateFormat.setLenient(false);

            List<Path> paths = null;
            try {
                paths = Files.list(Paths.get(dirPath)).collect(Collectors.toList());
                for (Path path : paths) {

                    String leagueId = FilenameUtils.removeExtension(path.getFileName().toString());
                    LOG.info("Fetching Season {}, LeagueId {} ", seasonId, leagueId);
                    League league = leagueDAO.getLeague(leagueId);
                    if (league == null) {
                        System.out.println();
                        LOG.warn("League {} not exist. Skip...", leagueId);
                        continue;
                    }

                    String countryId = league.getCountryId();

                    Reader reader = Files.newBufferedReader(path);
                    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

                    Set<String> notExistClubNames = new HashSet<>();
                    boolean first = true;
                    for (CSVRecord csvRecord : csvParser) {
                        if (first) {
                            first = false;
                            continue;
                        }

                        String homeClubName = csvRecord.get(CsvIndex.HOME_TEAM + csvIndexOffset);
                        String awayClubName = csvRecord.get(CsvIndex.AWAY_TEAM + csvIndexOffset);

                        Club homeClub = clubDAO.getClub(homeClubName, countryId);
                        if (homeClub == null) {
                            //LOG.warn("Home club {} not exist!", homeClubName);
                            notExistClubNames.add(homeClubName);
                            continue;
                        }

                        Club awayClub = clubDAO.getClub(awayClubName, countryId);
                        if (awayClub == null) {
                            //LOG.warn("Away club {} not exist!", awayClubName);
                            notExistClubNames.add(awayClubName);
                            continue;
                        }

                        String strPlayDate = csvRecord.get(CsvIndex.DATE) +
                                ((csvIndexOffset == 1) ? " " + csvRecord.get(CsvIndex.DATE + 1) : "");
                        Date playDate;
                        try {
                            playDate = dateFormat.parse(strPlayDate);
                        } catch (ParseException e) {
                            LOG.info("Date '{}' not valid!", strPlayDate);
                            continue;
                        }

                        /*LOG.info("Game {} - {} [season: {}, league: {}] not exist! Create now.",
                                homeClub.getName(), awayClub.getName(), leagueId, seasonId);*/

                        // collect home/away goal for full time and half time
                        boolean awarded = false;
                        String strHgFt = csvRecord.get(CsvIndex.HOME_GOALS_FT + csvIndexOffset);
                        String strAgFt = csvRecord.get(CsvIndex.AWAY_GOALS_FT + csvIndexOffset);
                        String strHgHt = csvRecord.get(CsvIndex.HOME_GOAL_HT + csvIndexOffset);
                        String strAgHt = csvRecord.get(CsvIndex.AWAY_GOAL_HT + csvIndexOffset);

                        Integer homeGoalsFt = null, awayGoalsFt = null,
                                homeGoalsHt = null, awayGoalsHt = null;
                        if (StringUtils.isBlank(strHgFt) || StringUtils.isBlank(strAgFt)) {
                            LOG.warn("Goals not available for match [Date:{} - {} vs {}]",
                                    strPlayDate, homeClubName, awayClubName);
                        } else {
                            homeGoalsFt = Integer.valueOf(strHgFt);
                            awayGoalsFt = Integer.valueOf(strAgFt);
                            if (StringUtils.isBlank(strHgHt) || StringUtils.isBlank(strAgHt)) {
                                LOG.warn("Awarded match found [Date:{} - {} vs {}]",
                                        strPlayDate, homeClubName, awayClubName);
                                strHgHt = strHgFt;
                                strAgHt = strAgFt;
                                awarded = true;
                            }
                            homeGoalsHt = Integer.valueOf(strHgHt);
                            awayGoalsHt = Integer.valueOf(strAgHt);
                        }

                        //collect home/away shots (all and on target separately)
                        int refereeOffset = ("EN".equals(countryId)) ? 1 : 0;
                        String strHs = csvRecord.get(CsvIndex.HOME_SHOTS +
                                csvIndexOffset + refereeOffset);
                        String strAs = csvRecord.get(CsvIndex.AWAY_SHOTS +
                                csvIndexOffset + refereeOffset);
                        String strHst = csvRecord.get(CsvIndex.HOME_SHOTS_OT +
                                csvIndexOffset + refereeOffset);
                        String strAst = csvRecord.get(CsvIndex.AWAY_SHOTS_OT +
                                csvIndexOffset + refereeOffset);

                        Integer homeShots = null, awayShots = null,
                                homeShotsOt = null, awayShotsOt = null;
                        if (StringUtils.isBlank(strHs) || StringUtils.isBlank(strAs) ||
                                StringUtils.isBlank(strHst) || StringUtils.isBlank(strAst)) {
                            LOG.warn("Shots not available for match [Date:{} - {} vs {}]",
                                    strPlayDate, homeClubName, awayClubName);
                        } else {
                            homeShots = Integer.valueOf(strHs);
                            awayShots = Integer.valueOf(strAs);
                            homeShotsOt = Integer.valueOf(strHst);
                            awayShotsOt = Integer.valueOf(strAst);
                        }


                        //collect home/away fouls
                        String strHf = csvRecord.get(CsvIndex.HOME_FOULS +
                                csvIndexOffset + refereeOffset);
                        String strAf = csvRecord.get(CsvIndex.AWAY_FOULS +
                                csvIndexOffset + refereeOffset);
                        Integer homeFouls = null, awayFouls = null;
                        if (StringUtils.isBlank(strHf) || StringUtils.isBlank(strAf)) {
                            LOG.warn("Fouls not available for match [Date:{} - {} vs {}]",
                                    strPlayDate, homeClubName, awayClubName);
                        } else {
                            homeFouls = Integer.valueOf(strHf);
                            awayFouls = Integer.valueOf(strAf);
                        }

                        //collect game odds (1, X, 2)
                        String strOdd1 = csvRecord.get(CsvIndex.HOME_ODD_BW +
                                csvIndexOffset + refereeOffset);
                        String strOddX = csvRecord.get(CsvIndex.DRAW_ODD_BW +
                                csvIndexOffset + refereeOffset);
                        String strOdd2 = csvRecord.get(CsvIndex.AWAY_ODD_BW +
                                csvIndexOffset + refereeOffset);
                        if (StringUtils.isBlank(strOdd1) || StringUtils.isBlank(strOddX)
                                || StringUtils.isBlank(strOdd2)) {

                            strOdd1 = csvRecord.get(CsvIndex.HOME_ODD_B365 +
                                    csvIndexOffset + refereeOffset);
                            strOddX = csvRecord.get(CsvIndex.DRAW_ODD_B365 +
                                    csvIndexOffset + refereeOffset);
                            strOdd2 = csvRecord.get(CsvIndex.AWAY_ODD_B365 +
                                    csvIndexOffset + refereeOffset);
                            if (StringUtils.isBlank(strOdd1) || StringUtils.isBlank(strOddX)
                                    || StringUtils.isBlank(strOdd2)) {

                                strOdd1 = csvRecord.get(CsvIndex.HOME_ODD_IW +
                                        csvIndexOffset + refereeOffset);
                                strOddX = csvRecord.get(CsvIndex.DRAW_ODD_IW +
                                        csvIndexOffset + refereeOffset);
                                strOdd2 = csvRecord.get(CsvIndex.AWAY_ODD_IW +
                                        csvIndexOffset + refereeOffset);
                            }
                        }

                        BigDecimal odd1 = null, oddX = null, odd2 = null;
                        if (StringUtils.isBlank(strOdd1) || StringUtils.isBlank(strOddX) ||
                                StringUtils.isBlank(strOdd2)) {
                            LOG.warn("Odds not available for match [Date:{} - {} vs {}]. Skip...",
                                    strPlayDate, homeClubName, awayClubName);
                        } else {
                            odd1 = new BigDecimal(strOdd1);
                            oddX = new BigDecimal(strOddX);
                            odd2 = new BigDecimal(strOdd2);
                            if (BigDecimal.ZERO.equals(odd1) || BigDecimal.ZERO.equals(oddX) ||
                                    BigDecimal.ZERO.equals(odd2)) {
                                LOG.warn("Zero Odd(s) found for match [Date:{} - {} vs {}]. Skip...",
                                        strPlayDate, homeClubName, awayClubName);
                                odd1 = null;
                                oddX = null;
                                odd2 = null;
                            }
                        }

                        Game existGame =
                                gameDAO.getGame(seasonId, leagueId,
                                        homeClub.getClubId(), awayClub.getClubId());
                        Game game = new Game(seasonId, leagueId, homeClub.getClubId(), awayClub.getClubId(),
                                playDate, homeGoalsFt, awayGoalsFt, homeGoalsHt, awayGoalsHt,
                                homeShots, awayShots, homeShotsOt, awayShotsOt, homeFouls, awayFouls,
                                odd1, oddX, odd2, awarded);
                        if (existGame == null) {
                            gameDAO.create(game);
                        } else if (existGame.getHomeGoalsFt() == null || existGame.getAwayGoalsFt() == null) {
                            game.setGameId(existGame.getGameId());
                            gameDAO.update(game);
                        }

                    }

                    if (!notExistClubNames.isEmpty()) {
                        LOG.warn("Not existing club list: {}",
                                notExistClubNames.stream().collect(Collectors.joining(",", "[", "]")));
                    }
                }
            } catch (Exception e) {
                LOG.error("Season {} - error occurred: {}", seasonId, LogHelper.errorMsg(e));
            }

        }

    }
}
