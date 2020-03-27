package com.betchart.model;

import java.math.BigDecimal;

public class GameResultEst {

    private String home;
    private String away;

    private BigDecimal est1;
    private BigDecimal estX;
    private BigDecimal est2;

    private BigDecimal positiveSurpriseIndex;
    private BigDecimal negativeSurpriseIndex;

    public GameResultEst(String home, String away,
                         BigDecimal est1, BigDecimal estX, BigDecimal est2,
                         BigDecimal positiveSurpriseIndex, BigDecimal negativeSurpriseIndex) {
        this.home = home;
        this.away = away;
        this.est1 = est1;
        this.estX = estX;
        this.est2 = est2;
        this.positiveSurpriseIndex = positiveSurpriseIndex;
        this.negativeSurpriseIndex = negativeSurpriseIndex;
    }

    public String getHome() {
        return home;
    }

    public String getAway() {
        return away;
    }

    public BigDecimal getEst1() {
        return est1;
    }

    public BigDecimal getEstX() {
        return estX;
    }

    public BigDecimal getEst2() {
        return est2;
    }

    public BigDecimal getPositiveSurpriseIndex() {
        return positiveSurpriseIndex;
    }

    public BigDecimal getNegativeSurpriseIndex() {
        return negativeSurpriseIndex;
    }
}
