package com.publicissapient.anaroc.command;

import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

import static com.publicissapient.anaroc.util.SeleniumConstants.HEIGHT_NOT_EQUALS;
import static com.publicissapient.anaroc.util.SeleniumConstants.NOT_A_NUMBER_ERR_MSG;
import static com.publicissapient.anaroc.util.SeleniumHelper.formatString;
import static com.publicissapient.anaroc.util.SeleniumHelper.isNumeric;

public class HeightCommand extends SeleniumActionWindow {

    public HeightCommand(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        WebElement element = webDriver.findElement(By.xpath(getXpath()));
        int actualHeight = element.getSize().getHeight();
        int expectedHeight = 0;
        if (isNumeric(getValue())) {
            expectedHeight = Integer.valueOf(getValue());
        } else {
            return getFailedResult(formatString(NOT_A_NUMBER_ERR_MSG.value(), "Height"));
        }

        return ((actualHeight == expectedHeight) ?
                getPassedResult() :
                getFailedResult(formatString(HEIGHT_NOT_EQUALS.value(), String.valueOf(expectedHeight), String.valueOf(actualHeight))));
    }
}
