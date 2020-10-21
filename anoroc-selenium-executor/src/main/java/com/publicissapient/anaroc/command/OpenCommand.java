package com.publicissapient.anaroc.command;

import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.Status;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class OpenCommand extends Command {

    public OpenCommand(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        String url = getXpath();
        webDriver.manage().window().maximize();
        webDriver.get(url);
        return Result.builder()
                .status(Status.PASSED)
                .build();
    }
}
