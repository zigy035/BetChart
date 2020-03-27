package com.betchart.dao;

import com.betchart.model.League;

import java.util.List;

public interface LeagueDAO {

    List<League> getLeagues();

    League getLeague(String leagueId);
}
