package com.betchart.importer;

import com.betchart.dao.ClubDAO;
import com.betchart.dao.LeagueDAO;
import com.betchart.model.Club;
import com.betchart.model.League;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

//@Component
public class HtmlClubDataImport {

    private static final String BASE_PATH = "D:\\Development\\CSV_FOOTBALL_DATA";
    private static final String CURRENT_SEASON = "2019-2020";

    private static final String FSC_PATH = "fsc_matches_update";

    @Autowired
    private LeagueDAO leagueDAO;

    @Autowired
    private ClubDAO clubDAO;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {

        try {
            int clubId = 1216;
            String dirPath = BASE_PATH + "\\" + FSC_PATH;
            List<Path> paths = Files.list(Paths.get(dirPath)).collect(Collectors.toList());
            for (Path path : paths) {
                //System.out.println(path.toString());

                if (path.toString().contains("_table")) {
                    File file = new File(path.toString());

                    String filename = FilenameUtils.removeExtension(path.getFileName().toString());
                    String leagueId = filename.split("_")[0];
                    League league = leagueDAO.getLeague(leagueId);
                    if (league == null) {
                        System.out.println("League " + leagueId + " not exist. Skip...");
                        continue;
                    }

                    String countryId = league.getCountryId();


                    Document doc = Jsoup.parse(file, "UTF-8");


                    /*
                    <span class="team_name_span">
                    <a onclick="javascript:getUrlByWinType('/team/paris-sg/CjhkPw0k/');">Paris SG</a>
                    </span>
                     */
                    Elements teamNameElements = doc.getElementsByClass("team_name_span");
                    for (Element element : teamNameElements) {
                        clubId++;
                        String name = element.getElementsByTag("a").first().text().trim();
                        //System.out.println(name);
                        String[] arr = element.getElementsByTag("a").attr("onclick").split("/");
                        String url = "/" + arr[2] + "/" + arr[3];

                        //System.out.println(url);

                        Club existClub = clubDAO.getClub(name, countryId);
                        if (existClub == null) {
                            System.out.println("Adding club '" + name + "' to '" + leagueId + "' league...");
                            Club club = new Club(clubId, countryId, name, url);
                            clubDAO.create(club);
                        }

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
