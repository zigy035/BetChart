package com.betchart.model;

import java.util.Date;

public class ScheduledItemParam {

    private Long scheduledItemParamId;
    private Long scheduledItemId;
    private String name;
    private String value;
    private Date createdDate;

    public Long getScheduledItemParamId() {
        return scheduledItemParamId;
    }

    public void setScheduledItemParamId(Long scheduledItemParamId) {
        this.scheduledItemParamId = scheduledItemParamId;
    }

    public Long getScheduledItemId() {
        return scheduledItemId;
    }

    public void setScheduledItemId(Long scheduledItemId) {
        this.scheduledItemId = scheduledItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
