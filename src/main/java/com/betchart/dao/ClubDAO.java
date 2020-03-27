package com.betchart.dao;

import com.betchart.model.Club;

import java.util.List;

public interface ClubDAO {

    Club getClub(Integer clubId);

    Club getClub(String clubName, String countryId);

    List<Club> getClubs(String leagueId);

    void create(Club club);

    void update(Club club);
}
