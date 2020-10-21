package com.publicissapient.anaroc.command;

import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.publicissapient.anaroc.util.SeleniumConstants.*;
import static com.publicissapient.anaroc.util.SeleniumHelper.formatString;

public class CloseTab extends SeleniumActionWindow {

    public CloseTab(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        String closeTabTitle = getValue();
        String currentWindowHandle = webDriver.getWindowHandle();
        Result result = getPassedResult();
        List<String> windowHandles = getWindowHandles();
        if((StringUtils.isEmpty(closeTabTitle) || StringUtils.isBlank(closeTabTitle))) {
            result = doCloseCurrentTab();
        }else {
            result = doCloseTabByTitle(closeTabTitle);
        }
        return result;
    }

    public void doClose(String windowHandle, WebDriver webDriver) {
        webDriver.findElement(By.cssSelector(HTML_TAG_BODY.value())).sendKeys(CLOSE_TAB.value());
    }

    public Result doCloseCurrentTab() {
        doClose(webDriver.getWindowHandle(), webDriver);
        return getPassedResult();
    }

    public Result doCloseTabByTitle(String closeTabTitle) {
        Optional<String> matchingWindowHandle = getWindowHandle(closeTabTitle);
        if(matchingWindowHandle.isPresent()){
            doClose(matchingWindowHandle.get(), webDriver.switchTo().window(matchingWindowHandle.get()));
            return getPassedResult();
        }else {
            return getFailedResult(formatString(TITLE_NOT_MATCH_FAILED_MSG.value(), webDriver.getTitle(), closeTabTitle));
        }
    }

    public boolean isWindowOpen(String windowHandle, WebDriver webDriver) {
        return webDriver.getWindowHandles().stream().anyMatch(wh -> StringUtils.equals(wh, windowHandle));
    }
}
