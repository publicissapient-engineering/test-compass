package com.publicissapient.anaroc.util;

import java.util.Arrays;

public enum WebDriverType {

    FIREFOX, CHROME, SAFARI, NONE;

    public static WebDriverType from(String typeName) {
        return Arrays.stream(WebDriverType.values()).filter(driverType -> driverType.name().toLowerCase().equals(typeName.toLowerCase())).findAny().orElse(WebDriverType.CHROME);
    }
}
