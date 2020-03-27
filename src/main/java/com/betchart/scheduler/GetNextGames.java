package com.betchart.scheduler;

import com.betchart.dao.ClubDAO;
import com.betchart.dao.GameDAO;
import com.betchart.dao.LeagueDAO;
import com.betchart.model.Game;
import com.betchart.model.League;
import com.betchart.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;

//@Component
public class GetNextGames {

    @Value("${base.url}")
    private String baseUrl;

    @Autowired
    private LeagueDAO leagueDAO;

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private ClubDAO clubDAO;

    private boolean isSync = false;

    //@Scheduled(fixedDelay = 1000)
    public void execute() {

        if (isSync) {
            return;
        }

        try {
            List<League> leagues = leagueDAO.getLeagues();
            List<String> skipLeagues =
                    Arrays.asList("IRE2", "KOR1", "CHI1", "JPN1", "JPN2");

            for (League league : leagues) {

                if (skipLeagues.contains(league.getLeagueId())) {
                    continue;
                }

                System.out.println("*** League: " + league.getName());
                String url = baseUrl + "/football" +
                        league.getUrl() + "/fixtures";
                String leagueId = league.getLeagueId();

                List<Game> games = null;
                //FscDataProvider.getNextGames(url);

                /*for (Game game : games) {
                    Game existGame = gameDAO.getGame(game.getGameId());
                    if (existGame == null) {
                        Club homeClub =
                                clubDAO.getClubByName(game.getHomeClubName());
                        Club awayClub =
                                clubDAO.getClubByName(game.getAwayClubName());

                        game.setHomeClubId(homeClub.getClubId());
                        game.setAwayClubId(awayClub.getClubId());
                        game.setLeagueId(leagueId);
                        gameDAO.create(game);
                    }
                }*/

                AppUtils.performMinDelay(5000);
            }

        } catch (Exception e) {
            isSync = true;
            e.printStackTrace();
        }

        isSync = true;
        System.out.println("****************************");
        System.out.println("********* FINISHED *********");
        System.out.println("****************************");
    }
}
