package com.betchart.scheduler;

import com.betchart.command.Command;
import com.betchart.model.ScheduledItem;
import com.betchart.service.ScheduledItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class FscMainScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(FscMainScheduler.class);

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private ScheduledItemService scheduledItemService;

    //@Scheduled(fixedDelay = 1000)
    public void run() {

        ScheduledItem item = scheduledItemService.getNextItem();
        if (item == null) {
            LOG.info("No new items scheduled!");
            return;
        }

        while (item != null) {

            Command command = (Command) ctx.getBean(item.getSpringBean());
            command.execute(item);

            item = scheduledItemService.getNextItem();
        }
    }
}
