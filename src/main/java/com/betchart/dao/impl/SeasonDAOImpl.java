package com.betchart.dao.impl;

import com.betchart.dao.SeasonDAO;
import com.betchart.model.Season;
import com.betchart.model.mapper.SeasonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SeasonDAOImpl extends JdbcBaseDAO implements SeasonDAO {

    @Override
    public List<Season> getSeasons() {
        return getJdbcTemplate().query("SELECT * FROM SEASON", new Object[] {}, new SeasonMapper());
    }
}
