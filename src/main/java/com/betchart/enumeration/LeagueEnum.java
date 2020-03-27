package com.betchart.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum LeagueEnum {

    ENGLAND (2, "Premier League"),
    GERMANY (3, "Bundesliga"),
    SPAIN (4, "La Liga"),
    ITALY (5, "Serie A"),
    GERMANY_2 (6, "Bundesliga II"),
    ENGLAND_2 (7, "Championship"),
    NETHERLANDS (13, "Eredivisie"),
    TURKEY (17, "Super Lig"),
    PORTUGAL (21, "Liga NOS"),
    FRANCE (22, "Ligue 1"),
    ARGENTINA (68, "Primera Division"),
    RUSSIA (77, "Premier League"),
    USA (85, "Major League Soccer"),
    BRAZIL (95, "Brasileirao"),
    CHINA (162, "Super league");

    private static final Map<Integer, LeagueEnum> codeMap = new HashMap<>();
    static {
        for (LeagueEnum league : LeagueEnum.values()) {
            codeMap.put(league.getCode(), league);
        }
    }

    private int code;
    private String name;

    LeagueEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static LeagueEnum get(int code) {
        return codeMap.get(code);
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getDomain() {
        return this.code + "-" + this.name.replace(" ", "-");
    }

}
