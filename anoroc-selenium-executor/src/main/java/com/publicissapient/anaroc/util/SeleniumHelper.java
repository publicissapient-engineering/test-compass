package com.publicissapient.anaroc.util;

public class SeleniumHelper {

    private SeleniumHelper() {
    }

    public static String formatString(String value, String ...args) {
        return String.format(value, args);
    }

    public static boolean isNumeric(String number) {
        try {
            int value = Integer.valueOf(number);
        } catch(NumberFormatException e){
            return false;
        }
        return true;
    }



}
