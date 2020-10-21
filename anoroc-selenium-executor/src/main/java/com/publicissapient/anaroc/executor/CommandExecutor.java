package com.publicissapient.anaroc.executor;

import com.publicissapient.anaroc.command.Command;
import com.publicissapient.anaroc.factory.CommandFactory;
import com.publicissapient.anaroc.parser.XPathParser;
import com.publicissapient.anoroc.model.Feature;
import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class CommandExecutor {

    private WebDriver webDriver;

    private Map<String, String> xPaths = new HashMap<>();

    private CommandExecutor(WebDriver webDriver, Map<String, String> xPaths) {
        this.webDriver = webDriver;
        this.xPaths = xPaths;
    }

    public static CommandExecutor build(WebDriver webDriver, Map<String, String> xPaths) {
        return new CommandExecutor(webDriver, xPaths);
    }

    public List<Feature> execute(List<Feature> features) {
        features.forEach(feature -> feature.getScenarioOutlines().forEach(scenarioOutline -> {
            scenarioOutline.getStepDefinitions().forEach(this::execute);
        }));
        return features;
    }

    public void execute(StepDefinition stepDefinition) {
        Command command = CommandFactory.build().getCommand(
                stepDefinition,
                webDriver,
                xPaths);
        Result executionResult = command.execute();
        stepDefinition.setResult(executionResult);
    }

    public CommandExecutor withWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
        return this;
    }

    public CommandExecutor withXPaths(Map<String, String> xPaths) {
        this.xPaths.putAll(xPaths);
        return this;
    }

    public CommandExecutor withXPaths(String fileName) {
        this.xPaths.putAll(XPathParser.xpaths(fileName));
        return this;
    }
    public CommandExecutor withXPathsString(String fileName) {
        this.xPaths.putAll(XPathParser.xpathsFromSoure(fileName));
        return this;
    }

}
