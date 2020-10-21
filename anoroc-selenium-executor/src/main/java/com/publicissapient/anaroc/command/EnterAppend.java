package com.publicissapient.anaroc.command;

import static com.publicissapient.anaroc.util.SeleniumConstants.*;
import static com.publicissapient.anaroc.util.SeleniumHelper.*;
import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

public class EnterAppend extends SeleniumActionWindow {

    public EnterAppend(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        WebElement element = webDriver.findElement(By.xpath(getXpath()));
        String textValue = element.getAttribute(ELEMENT_VALUE_ATTRIBUTE.value());
        String txtToAppend = getValue();
        String appendedText = new StringBuilder(textValue).append(txtToAppend).toString();
        element.sendKeys(getValue());
        return StringUtils.equals(appendedText, element.getText())
                ?   getPassedResult() :
                    getFailedResult(formatString(ENTER_APPEND_FAILED_MSG.value(), txtToAppend));
    }
}
