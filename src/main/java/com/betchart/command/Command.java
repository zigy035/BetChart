package com.betchart.command;

import com.betchart.model.ScheduledItem;

public interface Command {

	void execute(ScheduledItem item);
}
