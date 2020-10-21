package com.publicissapient.anaroc.command;

import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.Status;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

public class VerifyCommand extends Command {

    public VerifyCommand(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        String xpath = getXpath();
        String data = getValue();
        WebElement webElement = webDriver.findElement(By.xpath(xpath));
        Result result = Result.builder().build();
        if (isDataPresent(xpath, webElement, data)) {
            result.setStatus(Status.PASSED);
        } else {
            result.setStatus(Status.FAILED);
            result.setErrorMessage(data + " not present in " + xpath);
        }
        return result;
    }

    private boolean isDataPresent(String xpath, WebElement webElement, String data) {
        boolean isDataPresent = false;
        if (webElement != null && webDriver.findElement(By.xpath(xpath)).getText() != null) {
            String expText = webDriver.findElement(By.xpath(xpath)).getText();
            if (expText.contains(data)) {
                isDataPresent = true;
            }
        }
        return isDataPresent;
    }
}
