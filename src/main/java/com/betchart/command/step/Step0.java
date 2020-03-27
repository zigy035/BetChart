package com.betchart.command.step;

import com.betchart.command.Command;
import com.betchart.dao.LeagueDAO;
import com.betchart.enumeration.ScheduledItemStatus;
import com.betchart.model.League;
import com.betchart.model.ScheduledItem;
import com.betchart.service.ScheduledItemService;
import com.betchart.util.LogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

//@Component
public class Step0 implements Command {

    private static final Logger LOG = LoggerFactory.getLogger(Step0.class);

    @Autowired
    private LeagueDAO leagueDAO;

    @Autowired
    private ScheduledItemService scheduledItemService;

    @Value("${base.url}")
    private String baseUrl;

    @Override
    public void execute(ScheduledItem item) {
        try {
            if (item.getRetryCounter() > 2) {
                LOG.warn("Step0 [itemID: " + item.getScheduledItemId() + "] failed!");
                item.setStatus(ScheduledItemStatus.FAILED);
                scheduledItemService.update(item);
            }

            LOG.info("Step0 [itemID: " + item.getScheduledItemId() + "] in process...");
            List<League> leagues = leagueDAO.getLeagues();

            item.setStatus(ScheduledItemStatus.FINISHED);
        } catch (Exception e) {
            LOG.error(LogHelper.errorMsg(e));
            item.setRetryCounter(item.getRetryCounter() + 1);
            scheduledItemService.update(item);
        }
    }
}
