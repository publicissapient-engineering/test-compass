package com.publicissapient.anaroc.command;

import static com.publicissapient.anaroc.util.SeleniumConstants.*;
import com.publicissapient.anaroc.util.SeleniumHelper;
import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Map;

/**
 *
 */
public class ScrollDown extends SeleniumActionWindow {
    public ScrollDown(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        int pixel = 0;
        if(SeleniumHelper.isNumeric(getXpath())) {
            pixel = Integer.valueOf(getXpath());
        }else {
            return getFailedResult(SeleniumHelper.formatString(NOT_A_NUMBER_ERR_MSG.value(), "pixel"));
        }
        String js = SeleniumHelper.formatString(SCROLL_PAGE_DOWN.value(), String.valueOf(pixel));
        JavascriptExecutor jsExecutor = (JavascriptExecutor) webDriver;
        jsExecutor.executeScript(js);
        return getPassedResult();
    }
}
