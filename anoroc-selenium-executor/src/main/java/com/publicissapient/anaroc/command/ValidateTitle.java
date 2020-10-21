package com.publicissapient.anaroc.command;

import static  com.publicissapient.anaroc.util.SeleniumConstants.*;
import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class ValidateTitle extends SeleniumActionWindow {

    public ValidateTitle(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        String stringToVerify = getValue();
        String title = webDriver.getTitle();
        return StringUtils.equals(title, stringToVerify) ?  getPassedResult() : getFailedResult(VALIDATE_TITLE_FAILED_MSG.value());
    }
}
