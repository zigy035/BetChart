package com.betchart.service;

import com.betchart.model.ScheduledItemParam;

import java.util.Map;

public interface ScheduledItemParamService {

    Map<String, String> getParamMap(Long scheduledItemId);

    long create(ScheduledItemParam scheduledItemParam);
}
