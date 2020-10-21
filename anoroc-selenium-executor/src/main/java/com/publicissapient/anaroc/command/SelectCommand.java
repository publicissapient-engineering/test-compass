package com.publicissapient.anaroc.command;

import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.Status;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.Map;

public class SelectCommand extends Command {

    public SelectCommand(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        String xpath = getXpath();
        String value = getValue();
//
        Select select = new Select(webDriver.findElement(By.xpath(xpath)));
        WebElement element=  webDriver.findElement(By.xpath(xpath));
        highLight(element);

        select.selectByVisibleText(value);
        Actions action = new Actions(webDriver);
        action.sendKeys(Keys.ENTER);
        return Result.builder()
                .status(Status.PASSED)
                .build();
    }
}
