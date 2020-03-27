package com.betchart.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum ScheduledItemStatus {

	SCHEDULED (0),
	FINISHED (1),
	FAILED (2);
	
	private static final Map<Integer, ScheduledItemStatus> codeMap = new HashMap<>();
	static {
		for (ScheduledItemStatus status : ScheduledItemStatus.values()) {
			codeMap.put(status.getCode(), status);			
		}
	}
	
	private int code;
	
	private ScheduledItemStatus(int code) {
		this.code = code;
	}
	
	public static ScheduledItemStatus get(int code) {
		return codeMap.get(code);
	}
	
	public int getCode() {
		return this.code;
	}
	
}
