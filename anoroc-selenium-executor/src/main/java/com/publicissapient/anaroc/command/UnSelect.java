package com.publicissapient.anaroc.command;

import static  com.publicissapient.anaroc.util.SeleniumConstants.*;
import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

public class UnSelect extends SeleniumActionWindow {

    public UnSelect(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        WebElement element = webDriver.findElement(By.xpath(getXpath()));
        if(element.isSelected()) {
            element.click();
        }else {
            return getFailedResult(UNSELECT_FAILED_MSG.value());
        }
        return (!element.isSelected())?  getPassedResult() : getFailedResult(UNSELECT_FAILED_MSG_2.value());
    }
}
