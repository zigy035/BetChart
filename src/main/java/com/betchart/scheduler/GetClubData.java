package com.betchart.scheduler;

import com.betchart.dao.ClubDAO;
import com.betchart.dao.LeagueDAO;
import com.betchart.model.Club;
import com.betchart.model.League;
import com.betchart.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

//@Component
public class GetClubData {

    @Value("${base.url}")
    private String baseUrl;

    @Autowired
    private LeagueDAO leagueDAO;

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
            /*List<String> leagues =
                    Arrays.asList("IRE2", "KOR1", "CHI1", "JPN1", "JPN2");*/

            for (League league : leagues) {

                System.out.println("*** League: " + league.getName());
                String url = baseUrl + "/football" +
                        league.getUrl() + "/standings";

                String leagueId = league.getLeagueId();

                List<Club> clubs = null;
                //FscDataProvider.getClubs(url);

                for (Club club : clubs) {
                    Club existClub =
                            clubDAO.getClub(club.getClubId());
                    if (existClub == null) {
                        //club.setLeagueId(leagueId);
                        clubDAO.create(club);
                        System.out.println("ADD: " +
                                club.getClubId() + " - " +
                                club.getName());
                    } else {
                        //existClub.setMatchesPlayed(club.getMatchesPlayed());
                        //existClub.setGoalScored(club.getGoalScored());
                        //existClub.setGoalSuffered(club.getGoalSuffered());
                        clubDAO.update(existClub);
                        System.out.println("UPDATE: " +
                                existClub.getClubId() + " - " +
                                existClub.getName());
                    }
                }

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
