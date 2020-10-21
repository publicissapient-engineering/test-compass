package com.publicissapient.anaroc.command;

import com.publicissapient.anaroc.util.SeleniumConstants;
import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.Status;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

public class DeleteTextCommand extends Command {

    public DeleteTextCommand(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        String xPath = getXpath();
        String value = getValue();
        WebElement element=webDriver.findElement(By.xpath(xPath));
        highLight(element);
       // WebElement toClear = driver.findElement("locator");
        element.sendKeys(SeleniumConstants.SELECT_ALL.value());
        element.sendKeys(Keys.DELETE);
              //  element.sendKeys(value);






        return Result.builder()
                .status(Status.PASSED)
                .build();
    }
}
