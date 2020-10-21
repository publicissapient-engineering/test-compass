package com.publicissapient.anaroc.command;

import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.Status;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

public class ClearTextCommand extends Command {

    public ClearTextCommand(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        String xpath = getXpath();
        WebElement element = webDriver.findElement(By.xpath(xpath));
        highLight(element);
        element.clear();

        return Result.builder()
                .status(Status.PASSED)
                .build();
    }
}
