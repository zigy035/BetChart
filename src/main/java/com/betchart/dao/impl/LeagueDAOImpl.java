package com.betchart.dao.impl;

import com.betchart.dao.LeagueDAO;
import com.betchart.model.League;
import com.betchart.model.mapper.LeagueMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LeagueDAOImpl extends JdbcBaseDAO implements LeagueDAO {

    @Override
    public List<League> getLeagues() {
        return getJdbcTemplate().query("SELECT * FROM LEAGUE",
                new Object[]{}, new LeagueMapper());
    }

    @Override
    public League getLeague(String leagueId) {
        try {
            return getJdbcTemplate().queryForObject("SELECT * FROM LEAGUE " +
                            "WHERE LEAGUE_ID = ?",
                    new Object[] {leagueId},
                    new LeagueMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
