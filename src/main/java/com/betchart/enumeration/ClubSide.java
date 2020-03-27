package com.betchart.enumeration;

public enum ClubSide {

    HOME ("home"),
    AWAY ("away");

    /*private static final Map<Integer, ClubSide> codeMap = new HashMap<>();
    static {
        for (ClubSide side : ClubSide.values()) {
            codeMap.put(side.getCode(), league);
        }
    }*/

    private String code;

    ClubSide(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
