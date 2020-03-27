package com.betchart.dao;

import com.betchart.model.ScheduledItemParam;

import java.util.List;

public interface ScheduledItemParamDAO {

    List<ScheduledItemParam> getParams(Long scheduledItemId);

    long create(ScheduledItemParam scheduledItemParam);
}
