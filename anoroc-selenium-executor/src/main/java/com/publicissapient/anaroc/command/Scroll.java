package com.publicissapient.anaroc.command;

import com.publicissapient.anaroc.factory.ScrollCommandTypeFactory;
import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class Scroll extends SeleniumActionWindow {
    public Scroll(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        String scrollType = getValue();
        return ScrollCommandTypeFactory.getScrollType(stepDefinition, webDriver, xPaths, scrollType).executeWithSelenium();
    }
}
