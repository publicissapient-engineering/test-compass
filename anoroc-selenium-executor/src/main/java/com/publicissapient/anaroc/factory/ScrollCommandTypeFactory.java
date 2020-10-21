package com.publicissapient.anaroc.factory;

import com.publicissapient.anaroc.command.*;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class ScrollCommandTypeFactory {

    public static Command getScrollType(StepDefinition stepDefinition, WebDriver webDriver, Map<String, String> xPaths, String scrollType) {
        if(scrollType.contains("UP")) {
            return  new ScrollUp(stepDefinition, webDriver, xPaths);
        }else if(scrollType.contains("BOTTOM")) {
            return  new ScrollBottom(stepDefinition, webDriver, xPaths);
        }else if(scrollType.contains("TOP")) {
            return  new ScrollTop(stepDefinition, webDriver, xPaths);
        }else if(scrollType.contains("DOWN")) {
            return  new ScrollDown(stepDefinition, webDriver, xPaths);
        }else {
            return  new UnknownCommand(stepDefinition, webDriver, xPaths);
        }
    }
}
