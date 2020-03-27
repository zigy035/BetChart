package com.betchart.model.mapper;

import com.betchart.model.Season;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeasonMapper implements RowMapper<Season> {

    @Override
    public Season mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Season(
            rs.getString("SEASON_ID"),
            rs.getString("NAME"),
            rs.getInt("START_YEAR"),
            rs.getInt("END_YEAR"),
            rs.getString("CSV_URL")
        );
    }
}
