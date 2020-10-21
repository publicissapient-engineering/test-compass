package com.publicissapient.anaroc.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Map;

import static com.publicissapient.anaroc.factory.WebDriverFactory.ARGS;
import static com.publicissapient.anaroc.factory.WebDriverFactory.CAPABILITIES;

public class ChromeWebDriver {

    public static WebDriver getChromeDriver(Map<String, Object> args) throws Exception {
        String url = (String) args.get("remote.webdriver.url");
        return new RemoteWebDriver(new URL(url), getChromeOptions(args));
    }

    public static ChromeOptions getChromeOptions(Map<String, Object> args) {
        ChromeOptions options = new ChromeOptions();
        if (args.containsKey("webdriver.chrome.binary.path")) {
            options.setBinary((String) args.get("webdriver.chrome.binary.path"));
        }
        addArgs(options, args);
        setCapability(options, args);
        return options;
    }

    public static void addArgs(ChromeOptions chromeOptions, Map<String, Object> args) {
        chromeOptions.addArguments(ARGS);
    }

    public static void setCapability(ChromeOptions chromeOptions, Map<String, Object> args) {
        CAPABILITIES.entrySet().forEach((entry) -> chromeOptions.setCapability(entry.getKey(), entry.getValue()));

    }
}
