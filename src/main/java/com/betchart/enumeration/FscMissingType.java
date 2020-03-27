package com.betchart.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum FscMissingType {

    NONE (0),
    CARD (1),
    INJURY (2);

    private static final Map<Integer, FscMissingType> codeMap = new HashMap<>();
    static {
        for (FscMissingType mt : FscMissingType.values()) {
            codeMap.put(mt.getCode(), mt);
        }
    }

    private int code;

    FscMissingType(int code) {
        this.code = code;
    }

    public static FscMissingType get(int code) {
        return codeMap.get(code);
    }

    public int getCode() {
        return code;
    }
}
