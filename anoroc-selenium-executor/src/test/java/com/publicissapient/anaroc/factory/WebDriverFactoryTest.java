package com.publicissapient.anaroc.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class WebDriverFactoryTest {

    public static WebDriver localFireFoxWebDriver() throws Exception {
        return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), new FirefoxOptions());
    }

}
