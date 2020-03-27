package com.betchart.dao;

import com.betchart.model.ScheduledItem;

public interface ScheduledItemDAO {
	
	ScheduledItem getItem(Integer scheduledItemId);
	
	ScheduledItem getNextItem();
	
	long create(ScheduledItem scheduledItem);

    void update(ScheduledItem item);
}
