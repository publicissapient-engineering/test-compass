package com.publicissapient.anaroc.command;

import com.publicissapient.anaroc.util.SeleniumConstants;
import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Map;

/**
 *
 */
public class ScrollTop extends SeleniumActionWindow {
    public ScrollTop(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        jsExecutor.executeScript(SeleniumConstants.SCROLL_PAGE_TOP_JS.value());
        return getPassedResult();
    }
}
