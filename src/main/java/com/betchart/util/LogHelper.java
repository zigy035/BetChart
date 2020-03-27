package com.betchart.util;

public class LogHelper {

    public static String errorMsg(Exception e) {
        Throwable cause = e.getCause();
        String className = e.getClass().getName();
        if (cause == null) {
            return className + " - " + e.getMessage();
        }
        return className + " - " + cause.toString();
    }
}
