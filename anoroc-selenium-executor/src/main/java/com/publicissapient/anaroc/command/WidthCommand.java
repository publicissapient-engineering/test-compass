package com.publicissapient.anaroc.command;

import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

import static com.publicissapient.anaroc.util.SeleniumConstants.*;
import static com.publicissapient.anaroc.util.SeleniumHelper.formatString;
import static com.publicissapient.anaroc.util.SeleniumHelper.isNumeric;

public class WidthCommand extends SeleniumActionWindow {

    public WidthCommand(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        WebElement element = webDriver.findElement(By.xpath(getXpath()));
        int actualWidth = element.getSize().getWidth();
        int expectedWidth = 0;
        if (isNumeric(getValue())) {
            expectedWidth = Integer.valueOf(getValue());
        } else {
            return getFailedResult(formatString(NOT_A_NUMBER_ERR_MSG.value(), "Width"));
        }

        return ((actualWidth == expectedWidth) ?
                getPassedResult() :
                getFailedResult(formatString(WIDTH_NOT_EQUALS.value(),  String.valueOf(expectedWidth), String.valueOf(actualWidth))));
    }
}
