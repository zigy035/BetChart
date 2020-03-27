package com.betchart.model.mapper;

import com.betchart.model.ScheduledItemParam;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScheduledItemParamMapper implements RowMapper<ScheduledItemParam> {

    @Override
    public ScheduledItemParam mapRow(ResultSet rs, int rowNum) throws SQLException {
        ScheduledItemParam param = new ScheduledItemParam();
        param.setScheduledItemParamId(rs.getLong("scheduled_item_param_id"));
        param.setScheduledItemId(rs.getLong("scheduled_item_id"));
        param.setName(rs.getString("name"));
        param.setValue(rs.getString("value"));
        param.setCreatedDate(rs.getTimestamp("created_date"));
        return param;
    }
}
