package ru.kir.credit.web.utils;

import java.math.BigDecimal;

public class Validator {

    public static boolean checkString(String str) {

        if (str == null || str.isEmpty()) {
            return false;
        }

        return true;
    }

    public static boolean checkNumber(BigDecimal bigDecimal) {

        if (bigDecimal == null || bigDecimal.doubleValue() <= 0) {
            return false;
        }

        return true;
    }

    private Validator() {
    }

}
