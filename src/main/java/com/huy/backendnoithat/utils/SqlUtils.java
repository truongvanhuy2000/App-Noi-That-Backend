package com.huy.backendnoithat.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SqlUtils {
    public static String createLikeExpression(String value) {
        return "%" + value + "%";
    }
}
