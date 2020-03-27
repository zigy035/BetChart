package com.betchart.model.mapper;

import com.betchart.model.League;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LeagueMapper implements RowMapper<League> {

    @Override
    public League mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new League(
            rs.getString("league_id"),
            rs.getString("country_id"),
            rs.getString("name"),
            rs.getString("url")
        );
    }
}
