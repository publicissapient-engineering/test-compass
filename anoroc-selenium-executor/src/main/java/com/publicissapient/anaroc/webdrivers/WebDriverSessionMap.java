package com.publicissapient.anaroc.webdrivers;

import com.publicissapient.anaroc.factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WebDriverSessionMap {

    private final Map<String, WebDriver> sessions = new HashMap<>();

    private final Map<Session, WebDriver> sessionWebDriverMap =  new ConcurrentHashMap<>();

    public Session startDebugSession() throws Exception {
        Session newSession = createNewSessionId();
        sessionWebDriverMap.put(newSession, WebDriverFactory.localFireFoxWebDriver());
        return newSession;
    }

    public Session createNewSessionId() {
        Session session =  Session.builder().uuid(UUID.randomUUID().toString()).createdAt(new Date()).lastAccessed(new Date()).build();
        return session;
    }

    public boolean removeSession(Session session) {
        if(!isSessionAvailable(session)) {
            return false;
        }
        sessionWebDriverMap.remove(session);
        return true;
    }

    public boolean isSessionAvailable(Session session) {
        return Objects.nonNull(session) && sessionWebDriverMap.containsKey(session);
    }

    public WebDriver getWebDriver(Session session) {
        session.setLastAccessed(new Date());
        return sessionWebDriverMap.get(session);
    }

    public Map<Session, WebDriver> geSessionsMap() {

        return this.sessionWebDriverMap;
    }
}
