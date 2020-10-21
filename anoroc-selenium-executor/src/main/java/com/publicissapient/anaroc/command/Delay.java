package com.publicissapient.anaroc.command;

import static  com.publicissapient.anaroc.util.SeleniumConstants.*;

import com.publicissapient.anaroc.util.SeleniumHelper;
import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.WebDriver;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Delay extends SeleniumActionWindow {
    public Delay(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        int delayInSeconds = 0;
        try {
            delayInSeconds = Integer.parseInt(getValue());
        } catch (Exception e) {
            return getFailedResult(SeleniumHelper.formatString(DELAY_NOT_INT_ERR_MSG.value(), String.valueOf(delayInSeconds)));
        }
        webDriver.manage().timeouts().implicitlyWait(delayInSeconds, TimeUnit.SECONDS);
        return getPassedResult();
    }
}
