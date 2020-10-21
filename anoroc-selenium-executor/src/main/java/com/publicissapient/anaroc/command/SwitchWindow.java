package com.publicissapient.anaroc.command;

import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.WebDriver;

import java.util.Map;
import java.util.Optional;

import static com.publicissapient.anaroc.util.SeleniumConstants.*;
import static com.publicissapient.anaroc.util.SeleniumHelper.formatString;

public class SwitchWindow extends SeleniumActionWindow {
    public SwitchWindow(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        String tabTitle = getValue();
        Optional<String> optionalWH = getWindowHandle(tabTitle);
        if(optionalWH.isPresent()) {
            webDriver.switchTo().window(optionalWH.get());
            return getPassedResult();
        }
        return getFailedResult(formatString(SWITCH_WINDOW_ERROR_MSG.value(), tabTitle));
    }
}
