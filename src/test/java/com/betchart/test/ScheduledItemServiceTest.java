package com.betchart.test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.betchart.config.TestConfig;
import com.betchart.enumeration.ScheduledItemStatus;
import com.betchart.model.ScheduledItem;
import com.betchart.service.ScheduledItemService;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = TestConfig.class)
//@Transactional
//@Rollback(true)
public class ScheduledItemServiceTest {
	
	@Autowired
	private ScheduledItemService scheduledItemService;
	
	private ScheduledItem item;
	
	//@Before
	public void setup() {
		item = new ScheduledItem();
		item.setSpringBean("springBean");
		item.setStatus(ScheduledItemStatus.SCHEDULED);
		item.setRetryCounter(5);
		scheduledItemService.create(item);
	}
	
	//@Test
	public void testGetItem() {
		ScheduledItem nextItem = scheduledItemService.getNextItem();
		Assert.assertNotNull(nextItem);
		/*Assert.assertEquals(item.getSpringBean(), nextItem.getSpringBean());
		Assert.assertEquals(item.getRetryCounter(), nextItem.getRetryCounter());
		Assert.assertEquals(item.getStatus(), nextItem.getStatus());*/
	}
	
}
