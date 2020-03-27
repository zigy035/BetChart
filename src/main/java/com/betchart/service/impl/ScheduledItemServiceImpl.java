package com.betchart.service.impl;

import com.betchart.dao.ScheduledItemParamDAO;
import com.betchart.model.League;
import com.betchart.model.ScheduledItemParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betchart.dao.ScheduledItemDAO;
import com.betchart.model.ScheduledItem;
import com.betchart.service.ScheduledItemService;

import java.util.List;

@Service
@Transactional
public class ScheduledItemServiceImpl implements ScheduledItemService {
	
	@Autowired
	private ScheduledItemDAO scheduledItemDAO;

	@Autowired
	private ScheduledItemParamDAO scheduledItemParamDAO;
	
	@Override
	public ScheduledItem getItem(Integer scheduledItemId) {
		return scheduledItemDAO.getItem(scheduledItemId);
	}

	@Override
	public ScheduledItem getNextItem() {
		return scheduledItemDAO.getNextItem();
	}

	@Override
	public void create(ScheduledItem scheduledItem) {
		scheduledItemDAO.create(scheduledItem);
	}

	@Override
	public void create(ScheduledItem item, ScheduledItem nextItem, ScheduledItemParam... params) {
		scheduledItemDAO.update(item);
		long itemId = scheduledItemDAO.create(item);
		for (ScheduledItemParam param : params) {
			param.setScheduledItemId(itemId);
			scheduledItemParamDAO.create(param);
		}
	}

	@Override
	public void update(ScheduledItem item) {
		scheduledItemDAO.update(item);
	}

    @Override
    public void createLeagueItems(List<League> leagues, ScheduledItem item,
                                  ScheduledItem leagueItem, ScheduledItemParam leagueIdParam) {
        scheduledItemDAO.update(item);
        for (League league : leagues) {

            /*if (league.getDisabled()) {
                continue;
            }*//*if (league.getDisabled()) {
                continue;
            }*/

            long itemId = scheduledItemDAO.create(leagueItem);
            leagueIdParam.setScheduledItemId(itemId);
            leagueIdParam.setValue(league.getLeagueId());
            scheduledItemParamDAO.create(leagueIdParam);
        }
    }

}
