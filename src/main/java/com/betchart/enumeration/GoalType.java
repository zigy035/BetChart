package com.betchart.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum GoalType {

    NONE (0),
    NORMAL (1),
    OWN (2);

    private static final Map<Integer, GoalType> codeMap = new HashMap<>();
    static {
        for (GoalType gt : GoalType.values()) {
            codeMap.put(gt.getCode(), gt);
        }
    }

    private int code;

    GoalType(int code) {
        this.code = code;
    }

    public static GoalType get(int code) {
        return codeMap.get(code);
    }

    public int getCode() {
        return code;
    }
}
