package com.betchart.model.mapper;

import com.betchart.model.GameOdds;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameOddsMapper implements RowMapper<GameOdds> {

    @Override
    public GameOdds mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new GameOdds(
            rs.getBigDecimal("ODD_1"),
            rs.getBigDecimal("ODD_X"),
            rs.getBigDecimal("ODD_2")
        );
    }
}
