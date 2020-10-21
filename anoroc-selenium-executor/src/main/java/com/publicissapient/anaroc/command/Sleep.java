package com.publicissapient.anaroc.command;

import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class Sleep extends SeleniumActionWindow{
    public Sleep(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        try {
            int sleepInSecs = 0;
            sleepInSecs = Integer.parseInt(getValue()) * 1000;
            Thread.sleep(sleepInSecs);
        } catch (Exception e) {
            e.printStackTrace();
            getFailedResult(e.getMessage());
        }
        return getPassedResult();
    }
}
