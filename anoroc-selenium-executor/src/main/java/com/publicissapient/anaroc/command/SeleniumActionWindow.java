package com.publicissapient.anaroc.command;

import com.publicissapient.anoroc.model.StepDefinition;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class SeleniumActionWindow extends  Command {

    protected Actions actions;

    public SeleniumActionWindow(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
        actions = new Actions(webDriver);
    }

    public List<String> getWindowHandles() {
        return new ArrayList<>(webDriver.getWindowHandles());
    }

    protected Optional<String> getWindowHandle(String tabTitle) {
        return webDriver.getWindowHandles().stream().filter(wh -> this.isTitleMatch(wh, tabTitle)).findAny();
    }

    protected boolean isTitleMatch(String windowHandle, String closeTabTitle){
        String tabTitle =  webDriver.switchTo().window(webDriver.getWindowHandle()).getTitle();
        return StringUtils.equals(tabTitle, closeTabTitle);
    }

}
