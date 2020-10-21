package com.publicissapient.anaroc.command;

import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

import static com.publicissapient.anaroc.util.SeleniumConstants.*;
import static com.publicissapient.anaroc.util.SeleniumHelper.formatString;

public class SelectNodeFromTreeCommand extends SeleniumActionWindow {

    public SelectNodeFromTreeCommand(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        super(stepDefinition, webDriver, xPaths);
    }

    @Override
    protected Result executeWithSelenium() {
        String xpath = StringUtils.isEmpty(getXpath()) ? getValue() : getXpath();
        String[] nodes = xpath.split("/");
        WebElement targetElement = null;
        for(String node: nodes) {
            String formattedXpath = formatString(MAT_TREE_NODE_SPAN_ELE_XPATH.value(), node);
            WebElement element = webDriver.findElement(new By.ByXPath(formattedXpath));
            targetElement = findParentNodeContainsTagName(MAT_TREE_NODE_TAG_NAME.value(), element);
            clickExpandButtonElement(targetElement);
        }
        selectCheckboxElement(targetElement);
        return getPassedResult();
    }

    private WebElement findParentNodeContainsTagName(String tagName, WebElement element) {
        if(element.getTagName().equals(tagName)) {
            return element;
        }
        WebElement parent = (WebElement) ((JavascriptExecutor) webDriver).executeScript(PARENT_NODE_JS.value(), element);
        String parentTagName = parent.getTagName();
        return findParentNodeContainsTagName(tagName, parent);
    }

    private void clickExpandButtonElement(WebElement element) {
        List<WebElement> elementList = element.findElements(By.tagName("button"));
        if(CollectionUtils.isEmpty(elementList)) {
            return;
        }else {
            elementList.get(0).click();
        }
    }

    private void selectCheckboxElement(WebElement element) {
        List<WebElement> elementList = element.findElements(By.tagName("mat-checkbox"));
        if(CollectionUtils.isEmpty(elementList)) {
            return;
        }else {
            elementList.get(0).click();
        }
    }


}
