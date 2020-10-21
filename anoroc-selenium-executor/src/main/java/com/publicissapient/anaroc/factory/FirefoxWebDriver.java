package com.publicissapient.anaroc.factory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Map;

import static com.publicissapient.anaroc.factory.WebDriverFactory.ARGS;
import static com.publicissapient.anaroc.factory.WebDriverFactory.SELENIUM_HUB_URL;

public class FirefoxWebDriver {

    public static WebDriver getFirefoxWebDriver(Map<String, Object> args) throws Exception {
        String url = args.containsKey("remote.webdriver.url") ? (String)args.get("remote.webdriver.url") : SELENIUM_HUB_URL;
        return new RemoteWebDriver(new URL(url),getFirefoxOptions(args));
    }

    public static FirefoxOptions getFirefoxOptions(Map<String, Object> args) {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        if(MapUtils.isNotEmpty(args) && args.containsKey("webdriver.firefox.binary.path")) {
            firefoxOptions.setBinary((String)args.get("webdriver.firefox.binary.path"));
        }
        addArgs(firefoxOptions);
        setCapability(firefoxOptions);
        return firefoxOptions;
    }

    public static void addArgs(FirefoxOptions firefoxOptions) {
        // add --disable-extensions
        firefoxOptions.addArguments(ARGS);
    }

    public static void setCapability(FirefoxOptions firefoxOptions) {
        //add the key value pair
        firefoxOptions.setCapability("useAutomationExtension", "false");
    }

}
