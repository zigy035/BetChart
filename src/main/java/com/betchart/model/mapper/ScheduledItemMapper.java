package com.betchart.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.betchart.enumeration.ScheduledItemStatus;
import com.betchart.model.ScheduledItem;

public class ScheduledItemMapper implements RowMapper<ScheduledItem> {

	@Override
	public ScheduledItem mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		ScheduledItem scheduledItem = new ScheduledItem();
		scheduledItem.setScheduledItemId(resultSet.getLong("scheduled_item_id"));
		scheduledItem.setSpringBean(resultSet.getString("spring_bean"));
		scheduledItem.setStatus(ScheduledItemStatus.get(resultSet.getInt("status")));
		scheduledItem.setRetryCounter(resultSet.getInt("retry_counter"));
		scheduledItem.setCreatedDate(resultSet.getTimestamp("created_date"));
		return scheduledItem;
	}

}
