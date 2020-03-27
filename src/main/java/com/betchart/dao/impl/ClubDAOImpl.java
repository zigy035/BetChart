package com.betchart.dao.impl;

import com.betchart.dao.ClubDAO;
import com.betchart.model.Club;
import com.betchart.model.mapper.ClubMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClubDAOImpl extends JdbcBaseDAO implements ClubDAO {

    @Override
    public Club getClub(Integer clubId) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM CLUB WHERE CLUB_ID = ?",
                    new Object[] {clubId},
                    new ClubMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Club getClub(String clubName, String countryId) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM CLUB " +
                            "WHERE NAME = ? AND COUNTRY_ID = ?",
                            new Object[] {clubName, countryId},
                            new ClubMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Club> getClubs(String leagueId) {
        //TODO: fix this
        return getJdbcTemplate().query("SELECT * FROM CLUB WHERE LEAGUE_ID = ?",
                new Object[]{leagueId}, new ClubMapper());
    }

    @Override
    public void create(Club club) {
        getJdbcTemplate().update("INSERT INTO CLUB " +
                        "(CLUB_ID, COUNTRY_ID, NAME, URL) " +
                        "VALUES (?, ?, ?, ?)",
                        club.getClubId(),
                        club.getCountryId(),
                        club.getName(),
                        club.getUrl());
    }

    @Override
    public void update(Club club) {
        getJdbcTemplate().update("UPDATE CLUB SET URL = ? WHERE club_id = ?",
                club.getUrl(), club.getClubId());
    }
}
