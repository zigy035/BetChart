package com.betchart.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalOps {

    private static final RoundingMode ROUND_MODE = RoundingMode.DOWN;

    public static BigDecimal sum(BigDecimal... nums) {
        BigDecimal sum = new BigDecimal("0.00");
        for (BigDecimal num : nums) {
            BigDecimal tempNum = new BigDecimal(num.doubleValue()).setScale(2, ROUND_MODE);
            sum = sum.add(tempNum);
        }

        return sum;
    }

    public static BigDecimal substract(BigDecimal n1, BigDecimal n2) {
        BigDecimal tn1 = new BigDecimal(n1.doubleValue()).setScale(2, ROUND_MODE);
        BigDecimal tn2 = new BigDecimal(n2.doubleValue()).setScale(2, ROUND_MODE);
        return tn1.subtract(tn2);
    }

    public static BigDecimal multiply(BigDecimal n1, BigDecimal n2) {
        BigDecimal tn1 = new BigDecimal(n1.doubleValue()).setScale(2, ROUND_MODE);
        BigDecimal tn2 = new BigDecimal(n2.doubleValue()).setScale(2, ROUND_MODE);
        return tn1.multiply(tn2).setScale(2, ROUND_MODE);
    }

    public static BigDecimal divide(BigDecimal n1, BigDecimal n2) {
        BigDecimal tn1 = new BigDecimal(n1.doubleValue()).setScale(2, ROUND_MODE);
        BigDecimal tn2 = new BigDecimal(n2.doubleValue()).setScale(2, ROUND_MODE);
        return tn1.divide(tn2, 2);
    }

    public static int compare(BigDecimal n1, BigDecimal n2) {
        BigDecimal tn1 = new BigDecimal(n1.doubleValue()).setScale(2, ROUND_MODE);
        BigDecimal tn2 = new BigDecimal(n2.doubleValue()).setScale(2, ROUND_MODE);
        return tn1.compareTo(tn2);
    }

    public static void main(String[] args) {

        BigDecimal n1 = new BigDecimal("3.0");
        BigDecimal n2 = new BigDecimal("1.001");
        BigDecimal n3 = new BigDecimal("1.051");
        BigDecimal n4 = new BigDecimal("1.005");

        System.out.println(sum(n1, n2, n3));
        System.out.println(substract(n1, n2));
        System.out.println(multiply(n1, n2));
        System.out.println(divide(n2, n3));
        System.out.println(compare(n2, n4));
    }
}
