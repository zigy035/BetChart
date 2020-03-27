package com.betchart.model.mapper;

import com.betchart.model.Game;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameMapper implements RowMapper<Game> {

    @Override
    public Game mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Game(
            rs.getInt("GAME_ID"),
            rs.getString("SEASON_ID"),
            rs.getString("LEAGUE_ID"),
            rs.getInt("HOME_CLUB_ID"),
            rs.getInt("AWAY_CLUB_ID"),
            rs.getTimestamp("PLAY_DATE"),
            rs.getInt("HOME_GOALS_FT"),
            rs.getInt("AWAY_GOALS_FT"),
            rs.getInt("HOME_GOALS_HT"),
            rs.getInt("AWAY_GOALS_HT"),

            rs.getInt("HOME_SHOTS"),
            rs.getInt("AWAY_SHOTS"),
            rs.getInt("HOME_SHOTS_OT"),
            rs.getInt("AWAY_SHOTS_OT"),
            rs.getInt("HOME_FOULS"),
            rs.getInt("HOME_FOULS"),

            rs.getBigDecimal("ODD_1"),
            rs.getBigDecimal("ODD_X"),
            rs.getBigDecimal("ODD_2"),
            rs.getBoolean("IS_AWARDED")
        );
    }
}
