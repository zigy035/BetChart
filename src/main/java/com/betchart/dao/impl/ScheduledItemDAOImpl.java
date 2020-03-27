package com.betchart.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.betchart.dao.ScheduledItemDAO;
import com.betchart.model.ScheduledItem;
import com.betchart.model.mapper.ScheduledItemMapper;

@Repository
public class ScheduledItemDAOImpl extends JdbcBaseDAO implements ScheduledItemDAO {

/*	@Autowired
	private JdbcTemplate jdbcTemplate;
*/	
	@Override
	public ScheduledItem getItem(Integer scheduledItemId) {
		return getJdbcTemplate().queryForObject("SELECT * FROM scheduled_item " +
				"WHERE scheduled_item_id = ?",
				new Object[] {scheduledItemId}, new ScheduledItemMapper());
	}

	@Override
	public ScheduledItem getNextItem() {
		try {
			return getJdbcTemplate().queryForObject("SELECT * FROM scheduled_item " +
					"WHERE status = 0 " +
					"ORDER BY scheduled_item_id " +
					"LIMIT 1", new Object[] {}, new ScheduledItemMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public long create(ScheduledItem item) {
		KeyHolder holder = new GeneratedKeyHolder();
		getJdbcTemplate().update(getPreparedStatement("INSERT INTO scheduled_item " +
				"(spring_bean, retry_counter, status, created_date) " +
				"VALUES (?, ?, ?, NOW())",
				item.getSpringBean(), item.getRetryCounter(),
				item.getStatus().getCode()), holder);
		return holder.getKey().longValue();
	}

	@Override
	public void update(ScheduledItem item) {
		getJdbcTemplate().update("UPDATE scheduled_item " +
						"SET retry_counter = ?, status = ? " +
						"WHERE scheduled_item_id = ?",
				item.getRetryCounter(), item.getStatus().getCode(),
				item.getScheduledItemId());
	}

}
