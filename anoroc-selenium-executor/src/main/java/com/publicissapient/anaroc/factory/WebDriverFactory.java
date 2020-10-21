package com.publicissapient.anaroc.factory;

import com.publicissapient.anaroc.util.WebDriverType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.publicissapient.anaroc.factory.FirefoxWebDriver.getFirefoxOptions;

public class WebDriverFactory {

    public static String[] ARGS = {"--disable-extensions", "--diable-infobars"};
    public static String SELENIUM_HUB_URL = "http://localhost:4444/wd/hub";

    public static Map<String, String> CAPABILITIES = new HashMap<>();

    static {
        CAPABILITIES.put("useAutomationExtension", "false");
    }

    public static WebDriver localFireFoxWebDriver() throws Exception {
        return new RemoteWebDriver(new URL(SELENIUM_HUB_URL), getFirefoxOptions(Collections.emptyMap()));
    }

    public static WebDriver getWebDriver(Map<String, Object> args) throws Exception {
        return ((args.isEmpty()) ? localFireFoxWebDriver() : getDriver(args));
    }

    public static WebDriver getDriver(Map<String, Object> args) throws Exception {
        WebDriverType driverType = WebDriverType.from((String) args.get("webdriver.type"));
        WebDriver webDriver = null;
        switch (driverType) {
            case SAFARI:
                break;
            case CHROME:
                webDriver = ChromeWebDriver.getChromeDriver(args);
                break;
            case FIREFOX:
            default:
                webDriver = FirefoxWebDriver.getFirefoxWebDriver(args);
                break;
        }
        return webDriver;
    }


}
