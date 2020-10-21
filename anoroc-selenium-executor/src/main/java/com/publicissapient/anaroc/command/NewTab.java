package com.publicissapient.anaroc.command;

import com.publicissapient.anaroc.util.SeleniumConstants;
import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class NewTab extends SeleniumActionWindow {

    public NewTab(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        String openJs = String.format(SeleniumConstants.WINDOW_OPEN_JS.value(), getXpath());
        executor.executeScript(openJs);
        return getPassedResult();
    }
}
