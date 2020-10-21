package com.publicissapient.anaroc.command;

import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.Status;
import com.publicissapient.anoroc.model.StepDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@Data
public abstract class Command{

    protected StepDefinition stepDefinition;

    protected WebDriver webDriver;

    protected Map<String, String> xPaths;

    protected String parentWindowHandle;

    public Command(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths) {
        this.stepDefinition = stepDefinition;
        this.webDriver = webDriver;
        this.xPaths = xPaths;
    }

    public Result execute() {
        Instant startTime = Instant.now();
        Result result = Result.builder().status(Status.FAILED).build();
        try {
            result = executeWithSelenium();
        } catch (Exception e) {
            result.setErrorMessage(e.getMessage());
        }
        Instant endTime = Instant.now();
        result.setDurationInNano(Duration.between(startTime, endTime).toNanos());
        return result;
    }

    void highLight(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
    }

    protected String getValue() {
        return stepDefinition.getData().values().iterator().next();
    }

    protected  String getXpath(){
        return xPaths.values().iterator().next();
    }

    protected  String getXpathByKey(String key){
        return xPaths.get(key);
    }

    protected  Result getPassedResult() {
        return Result.builder().status(Status.PASSED).build();
    }

    protected  Result getFailedResult() {
        return Result.builder().status(Status.FAILED).build();
    }

    protected  Result getFailedResult(String errorMessage) {
        return Result.builder().status(Status.FAILED).errorMessage(errorMessage).build();
    }

    protected void setParentWindowHandle() {
        parentWindowHandle = webDriver.getWindowHandle();
    }

    protected abstract Result executeWithSelenium();
}
