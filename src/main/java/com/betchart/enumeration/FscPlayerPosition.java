package com.betchart.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum FscPlayerPosition {

    GK (0, "Goalkeepers"),
    DF (1, "Defenders"),
    MF (2, "Midfielders"),
    FW (3, "Forwards");

    private static final Map<Integer, FscPlayerPosition> codeMap = new HashMap<>();
    private static final Map<String, FscPlayerPosition> nameMap = new HashMap<>();

    static {
        for (FscPlayerPosition p : FscPlayerPosition.values()) {
            codeMap.put(p.getCode(), p);
            nameMap.put(p.getName(), p);
        }
    }

    private int code;
    private String name;

    FscPlayerPosition(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static FscPlayerPosition get(int code) {
        return codeMap.get(code);
    }

    public static FscPlayerPosition byName(String name) {
        return nameMap.get(name);
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
