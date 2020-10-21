package com.publicissapient.anaroc.command;

import com.publicissapient.anaroc.util.SeleniumConstants;
import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

/**
 *
 */
public class ScrollFind extends SeleniumActionWindow {
    public ScrollFind(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        WebElement element = webDriver.findElement(By.xpath(getXpath()));
        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        jsExecutor.executeScript(SeleniumConstants.SCROLL_TILL_FIND_ELEMENT.value(), element);
        return getPassedResult();
    }
}
