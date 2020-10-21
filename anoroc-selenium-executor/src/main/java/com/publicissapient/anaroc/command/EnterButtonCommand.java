package com.publicissapient.anaroc.command;

import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

public class EnterButtonCommand extends SeleniumActionWindow {

    public EnterButtonCommand(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        WebElement element = webDriver.findElement(By.xpath(getXpath()));
        element.sendKeys(Keys.ENTER);
        return getPassedResult();
    }
}
