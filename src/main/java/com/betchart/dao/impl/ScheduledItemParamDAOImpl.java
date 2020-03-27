package com.betchart.dao.impl;

import com.betchart.dao.ScheduledItemParamDAO;
import com.betchart.model.ScheduledItemParam;
import com.betchart.model.mapper.ScheduledItemParamMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScheduledItemParamDAOImpl extends JdbcBaseDAO implements ScheduledItemParamDAO {

    @Override
    public List<ScheduledItemParam> getParams(Long scheduledItemId) {
        return getJdbcTemplate().query("SELECT * FROM scheduled_item_param " +
                "WHERE scheduled_item_id = ?",
                new Object[]{scheduledItemId}, new ScheduledItemParamMapper());
    }

    @Override
    public long create(ScheduledItemParam scheduledItemParam) {
        KeyHolder holder = new GeneratedKeyHolder();
        getJdbcTemplate().update(
                getPreparedStatement("INSERT INTO scheduled_item_param " +
                        "(scheduled_item_id, name, value, created_date) " +
                        "VALUES (?, ?, ?, NOW())",
                        scheduledItemParam.getScheduledItemId(),
                        scheduledItemParam.getName(),
                        scheduledItemParam.getValue()), holder);
        return holder.getKey().longValue();
    }

}
