package com.betchart.model;

import java.math.BigDecimal;

public class GameOdds {

    private BigDecimal odd1;
    private BigDecimal oddX;
    private BigDecimal odd2;

    public GameOdds(BigDecimal odd1, BigDecimal oddX, BigDecimal odd2) {
        this.odd1 = odd1;
        this.oddX = oddX;
        this.odd2 = odd2;
    }

    public BigDecimal getOdd1() {
        return odd1;
    }

    public BigDecimal getOddX() {
        return oddX;
    }

    public BigDecimal getOdd2() {
        return odd2;
    }

    public BigDecimal getMinOdd() {
        if (this.odd1 == null | this.oddX == null | this.odd2 == null) {
            return null;
        }

        double min = Math.min(this.odd1.doubleValue(), this.odd2.doubleValue());
        return new BigDecimal(min);
    }
}
