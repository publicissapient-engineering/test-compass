package com.publicissapient.anaroc.command;

import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.Status;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class HighlightCommand extends Command {

    public HighlightCommand(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        String xpath = getXpath();
        String actText = getValue();
        String expText = webDriver.findElement(By.xpath(xpath)).getText();
        if(expText.contains(actText)) {
            return Result.builder()
                    .status(Status.PASSED)
                    .build();
        } else {
            return Result.builder()
                    .status(Status.PASSED)
                    .build();
        }

    }
}
