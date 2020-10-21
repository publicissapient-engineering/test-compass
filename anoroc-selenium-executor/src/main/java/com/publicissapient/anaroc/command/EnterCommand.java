package com.publicissapient.anaroc.command;

import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.Status;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

public class EnterCommand extends Command {

    public EnterCommand(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        String xPath = getXpath();
        String value = getValue();
        WebElement element=webDriver.findElement(By.xpath(xPath));
        highLight(element);
                element.sendKeys(value);

        return Result.builder()
                .status(Status.PASSED)
                .build();
    }
}
