package com.betchart.model.mapper;

import com.betchart.model.Club;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClubMapper implements RowMapper<Club> {

    @Override
    public Club mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Club(
            rs.getInt("CLUB_ID"),
            rs.getString("COUNTRY_ID"),
            rs.getString("NAME"),
            rs.getString("URL")
        );
    }
}
