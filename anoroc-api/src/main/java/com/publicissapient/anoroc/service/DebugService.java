package com.publicissapient.anoroc.service;

import com.publicissapient.anaroc.executor.CommandExecutor;
import com.publicissapient.anaroc.factory.DebugSessionFactory;
import com.publicissapient.anaroc.factory.WebDriverFactory;
import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DebugService {

    @Autowired
    private DebugSessionFactory sessionFactory;

    public boolean startDebug(String sessionId) throws Exception {
        return sessionFactory.add(sessionId, WebDriverFactory.localFireFoxWebDriver());
    }


    public boolean stopDebug(String sessionId) {
        return sessionFactory.delete(sessionId);
    }


    public Result execute(String sessionId, StepDefinition stepDefinition, Map<String, String> xpaths) {
        new CommandExecutor().withWebDriver(sessionFactory.get(sessionId)).withXPaths(xpaths).execute(stepDefinition);
        return stepDefinition.getResult();
    }

}
