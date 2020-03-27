package com.betchart.model;

import java.util.Date;

import com.betchart.enumeration.ScheduledItemStatus;

public class ScheduledItem {
	
	private Long scheduledItemId;
	private String springBean;
	private Integer	retryCounter;
	private ScheduledItemStatus status; 
	private Date createdDate;

	public Long getScheduledItemId() {
		return scheduledItemId;
	}
	public void setScheduledItemId(Long scheduledItemId) {
		this.scheduledItemId = scheduledItemId;
	}
	
	public String getSpringBean() {
		return springBean;
	}
	public void setSpringBean(String springBean) {
		this.springBean = springBean;
	}
	
	public Integer getRetryCounter() {
		return retryCounter;
	}
	public void setRetryCounter(Integer retryCounter) {
		this.retryCounter = retryCounter;
	}
	
	public ScheduledItemStatus getStatus() {
		return status;
	}
	public void setStatus(ScheduledItemStatus status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
