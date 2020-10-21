package com.publicissapient.anoroc.executors.anoroc;

import com.publicissapient.anaroc.executor.CommandExecutor;
import com.publicissapient.anaroc.factory.WebDriverFactory;
import com.publicissapient.anoroc.exception.FeatureNotFoundException;
import com.publicissapient.anoroc.messaging.payload.RunPayload;
import com.publicissapient.anoroc.model.Feature;
import com.publicissapient.anoroc.model.ScenarioOutline;
import com.publicissapient.anoroc.model.ScreenShot;
import com.publicissapient.anoroc.model.StepDefinition;
import com.publicissapient.anoroc.parser.FeatureParser;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AnorocExecutionEngine {

    public List<Feature> executeFeature(RunPayload run) throws Exception {
        WebDriver webDriver = null;
        try {
            webDriver = WebDriverFactory.getWebDriver(run.getArgs());
            FeatureParser featureParser = new FeatureParser();
            CommandExecutor commandExecutor = new CommandExecutor();
            return executeFeatures(webDriver, run, featureParser, commandExecutor);
        } catch (Exception exception) {
            throw exception;
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
        }
    }

    private List<Feature> executeFeatures(WebDriver webDriver, RunPayload run, FeatureParser featureParser, CommandExecutor commandExecutor) {

        log.info("Running the functional tests for " + run.getName());
        List<Feature> featureFromContent = featureParser.features(run.getContent(), run.getName());
        executeFeatures(featureFromContent, webDriver, run, commandExecutor);
        List<Feature> featuresExecuted = new ArrayList<>();
        log.info("Feature content successfully parsed");
        featuresExecuted.addAll(featureFromContent);


        return featuresExecuted;
    }

    private void checkContentEmpty(RunPayload run) {
        if (run.getContent().isEmpty()) {
            throw new FeatureNotFoundException("Feature content is empty!!");
        }

        if (run.getXpath().isEmpty()) {
            run.setXpath("{\n" +
                    "  \n" +
                    "}");
        }
    }

    private void executeFeatures(List<Feature> features, WebDriver webDriver, RunPayload run, CommandExecutor commandExecutor) {
        commandExecutor
                .withWebDriver(webDriver)
                .withXPaths(convertMapValueToString(run.getArgs()))
                .withXPathsString(run.getXpath());

        log.info("Started executing functional tests on feature " + run.getName());
        features.forEach(
                feature -> {
                    log.info("\tFeature: " + feature.getName());
                    executeScenarioOutlines(feature, webDriver, commandExecutor);
                }
        );

        log.info("Completed executing feature " + run.getName());
    }

    private void executeScenarioOutlines(Feature feature, WebDriver webDriver, CommandExecutor commandExecutor) {

        feature.getScenarioOutlines().forEach(scenarioOutline -> {
            log.info("\t\tScenario Outline: " + scenarioOutline.getName());
            executeStepDefinition(scenarioOutline, webDriver, commandExecutor);
        });
    }

    private void executeStepDefinition(ScenarioOutline scenarioOutline, WebDriver webDriver, CommandExecutor commandExecutor) {
        scenarioOutline.getStepDefinitions().forEach(stepDefinition -> {
            commandExecutor.execute(stepDefinition);
            updateScreenShots(stepDefinition, webDriver);
            displayResult(stepDefinition);
        });
    }

    private void updateScreenShots(StepDefinition stepDefinition, WebDriver webDriver) {
        if (!stepDefinition.getResult().getStatus().isPassed()) {
            TakesScreenshot screenshot = (TakesScreenshot) webDriver;
            ScreenShot screenShot = new ScreenShot();
            screenShot.setName(stepDefinition.getInstruction());
            screenShot.setData(screenshot.getScreenshotAs(OutputType.BASE64));
            if (stepDefinition.getResult().getScreenShots() == null) {
                stepDefinition.getResult().setScreenShots(new ArrayList<>());
            }
            stepDefinition.getResult().getScreenShots().add(screenShot);
        }
    }

    private void displayResult(StepDefinition stepDefinition) {
        String instruction = stepDefinition.getInstruction();
        if (!stepDefinition.getData().isEmpty()) {
            for (String key : stepDefinition.getData().keySet()) {
                instruction = instruction.replace("<" + key + ">", stepDefinition.getData().get(key));
            }
        }
        StringBuilder result = new StringBuilder()
                .append("\t\t\t")
                .append(stepDefinition.getKeyword())
                .append(instruction)
                .append(" - ")
                .append(stepDefinition.getResult().getStatus().getRawName())
                .append(" (")
                .append(TimeUnit.MILLISECONDS.convert(stepDefinition.getResult().getDurationInNano(), TimeUnit.NANOSECONDS))
                .append(" ms)");
        if (stepDefinition.getResult().getStatus().isPassed()) {
            log.info(result.toString());
        } else {
            log.info(result.toString());
            log.info(stepDefinition.getResult().getErrorMessage());
        }
    }

    private Map<String, String> convertMapValueToString(Map<String, Object> envArgs) {
        return envArgs.entrySet().stream().collect(Collectors.toMap(this::getKey, this::getValue));
    }

    private String getKey(Map.Entry<String, Object> entry) {
        return entry.getKey();
    }

    private String getValue(Map.Entry<String, Object> entry) {
        return (String) entry.getValue();
    }

}
