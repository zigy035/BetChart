package com.betchart.importer;

import com.betchart.dao.ClubDAO;
import com.betchart.dao.GameDAO;
import com.betchart.dao.LeagueDAO;
import com.betchart.model.Club;
import com.betchart.model.Game;
import com.betchart.model.League;
import com.betchart.util.LogHelper;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HtmlGameDataImport {

    private static final Logger LOG = LoggerFactory.getLogger(CsvGameDataImport.class);

    private static final String BASE_PATH = "D:\\Development\\CSV_FOOTBALL_DATA";
    private static final String FSC_PATH = "fsc_matches_update";
    private static final String CURRENT_SEASON = "2019-2020";

    @Autowired
    private LeagueDAO leagueDAO;

    @Autowired
    private ClubDAO clubDAO;

    @Autowired
    private GameDAO gameDAO;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        try {
            String dirPath = BASE_PATH + "\\" + FSC_PATH;
            List<Path> paths = Files.list(Paths.get(dirPath)).collect(Collectors.toList());
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            dateFormat.setLenient(false);
            for (Path path : paths) {
                if (!path.toString().contains("_table")) {
                    File file = new File(path.toString());

                    String leagueId = FilenameUtils.removeExtension(path.getFileName().toString());

                    League league = leagueDAO.getLeague(leagueId);
                    if (league == null) {
                        System.out.println("League " + leagueId + " not exist. Skip...");
                        continue;
                    }

                    String countryId = league.getCountryId();

                    Document doc = Jsoup.parse(file, "UTF-8");
                    Elements matches = doc.getElementsByClass("event__match");
                    Set<String> notExistClubs = new HashSet<>();
                    for (Element match : matches) {
                        String time = match.getElementsByClass("event__time").first().text();
                        String[] timeParts = time.split("\\.");
                        int month = Integer.valueOf(timeParts[1]);
                        time = time.replace(". ", (month > 7) ? ".2019 " : ".2020 ");
                        String homeClubName = match.getElementsByClass("event__participant--home").first().text();
                        String awayClubName = match.getElementsByClass("event__participant--away").first().text();
                        Club homeClub = clubDAO.getClub(homeClubName, countryId);
                        Club awayClub = clubDAO.getClub(awayClubName, countryId);
                        if (homeClub == null) {
                            notExistClubs.add(homeClubName);
                            continue;
                        }
                        if (awayClub == null) {
                            notExistClubs.add(awayClubName);
                            continue;
                        }

                        Integer homeClubId = homeClub.getClubId();
                        Integer awayClubId = awayClub.getClubId();

                        Game existGame = gameDAO.getGame(CURRENT_SEASON, leagueId, homeClubId, awayClubId);
                        if (existGame == null) {

                            Game game = new Game(CURRENT_SEASON, leagueId, homeClubId, awayClubId, dateFormat.parse(time),
                                    null, null, null, null, null, null, null, null, null, null, null, null, null, false);
                            gameDAO.create(game);
                        }

                        LOG.info("Time: {} - {} vs {}", time, homeClubName, awayClubName);
                    }

                    for (String notExistClub : notExistClubs) {
                        LOG.warn("Club {} not exist!", notExistClub);
                    }

                }
            }
        } catch (Exception e) {
            LOG.error(LogHelper.errorMsg(e));
        }
    }
}
