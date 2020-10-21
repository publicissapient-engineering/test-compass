package com.publicissapient.anaroc.factory;

import com.publicissapient.anaroc.exception.DebugSessionInvalidException;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DebugSessionFactory {

    private Map<String, WebDriver> webDriverMap = new HashMap<>();

    public boolean add(String sessionId, WebDriver webDriver) {
        webDriverMap.put(sessionId, webDriver);
        return true;
    }

    public WebDriver get(String sessionId) throws DebugSessionInvalidException {
        WebDriver webDriver = webDriverMap.get(sessionId);
        if(Objects.isNull(webDriver)) {
            throw new DebugSessionInvalidException("Invalid session id");
        }
        return webDriver;
    }

    public boolean delete(String sessionId) {
        WebDriver webDriver = get(sessionId);
        webDriver.quit();
        webDriverMap.remove(sessionId);
        return true;
    }

    public void closeAllSessions() {
        webDriverMap.values().stream().forEach(webDriver -> webDriver.quit());
    }


}
