package com.betchart.service;

import com.betchart.model.League;
import com.betchart.model.ScheduledItem;
import com.betchart.model.ScheduledItemParam;

import java.util.List;

public interface ScheduledItemService {
	
	ScheduledItem getItem(Integer scheduledItemId);
	
	ScheduledItem getNextItem();
	
	void create(ScheduledItem scheduledItem);

	void create(ScheduledItem item, ScheduledItem nextItem, ScheduledItemParam... params);

	void update(ScheduledItem item);

    void createLeagueItems(List<League> leagues, ScheduledItem item,
                           ScheduledItem leagueItem, ScheduledItemParam leagueIdParam);
}
