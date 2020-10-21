package com.publicissapient.anaroc.command;

import com.publicissapient.anaroc.util.SeleniumConstants;
import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.Status;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.stream.Stream;

public class HoverCommand extends SeleniumActionWindow {

    public HoverCommand(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        String xpath = getXpath();
        Stream<String> xpathStream = null;
        if(xpath.contains(SeleniumConstants.COMMA_SEPARATOR.value())) {
            xpathStream = Stream.of(xpath.split(SeleniumConstants.COMMA_SEPARATOR.value()));
        }else {
            xpathStream = Stream.of(xpath);
        }
        xpathStream.forEach(this::acceptXpath);
        actions.build().perform();
        return Result.builder().status(Status.PASSED).build();
    }

    private void acceptXpath(String xPath) {
        WebElement element = webDriver.findElement(By.xpath(xPath));
        actions = actions.moveToElement(element);
    }
}
