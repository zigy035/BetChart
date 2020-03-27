package com.betchart.service.impl;

import com.betchart.dao.ScheduledItemParamDAO;
import com.betchart.model.ScheduledItemParam;
import com.betchart.service.ScheduledItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ScheduledItemParamServiceImpl implements ScheduledItemParamService {

    @Autowired
    private ScheduledItemParamDAO scheduledItemParamDAO;


    @Override
    public Map<String, String> getParamMap(Long scheduledItemId) {
        List<ScheduledItemParam> params = scheduledItemParamDAO.getParams(scheduledItemId);

        Map<String, String> paramMap = new HashMap<>();
        for (ScheduledItemParam param : params) {
            paramMap.put(param.getName(), param.getValue());
        }

        return paramMap;
    }

    @Override
    public long create(ScheduledItemParam scheduledItemParam) {
        return scheduledItemParamDAO.create(scheduledItemParam);
    }
}
