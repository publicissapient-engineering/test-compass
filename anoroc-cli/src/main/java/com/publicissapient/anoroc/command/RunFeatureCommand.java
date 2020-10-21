package com.publicissapient.anoroc.command;

import com.publicissapient.anaroc.executor.CommandExecutor;
import com.publicissapient.anaroc.factory.WebDriverFactory;
import com.publicissapient.anoroc.AnorocReporting;
import com.publicissapient.anoroc.dto.Report;
import com.publicissapient.anoroc.model.Feature;
import com.publicissapient.anoroc.model.ScenarioOutline;
import com.publicissapient.anoroc.model.ScreenShot;
import com.publicissapient.anoroc.model.StepDefinition;
import com.publicissapient.anoroc.parser.FeatureParser;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.publicissapient.anoroc.printer.ConsolePrinter.*;

@ShellComponent
public class RunFeatureCommand {

    public static final String FEATURE_FILE_EXTENSION = ".feature";
    public static final String JSON_FILE_EXTENSION = ".json";
    public static final String FILE_DIRECTORY = System.getProperty("user.dir") + "/features/";

    @Autowired
    private FeatureParser featureParser;

    @Autowired
    private CommandExecutor commandExecutor;

    @Autowired
    private AnorocReporting anorocReporting;

    private WebDriver webDriver = null;

    @ShellMethod("Run features from multiple feature files.")
    public void run(String... features) {
        try {
            webDriver = WebDriverFactory.localFireFoxWebDriver();
            Date startTime = new Date();
            printlnCyan("Features execution started at " + startTime);
            List<Feature> featuresExecuted = executeFeatures(webDriver, features);
            Date endTime = new Date();
            printlnCyan("Features execution completed at " + endTime);
            displayExecutionTime(startTime, endTime);

            Report report = generateHTMLReport(featuresExecuted);
            printlnCyan("Reported Generated in " + report.getHtmlFiles());

        } catch (Exception e) {
            printlnRed(e.getMessage());
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
        }
    }

    private List<Feature> executeFeatures(WebDriver webDriver, String[] features) {
        List<Feature> featuresExecuted = new ArrayList<>();
        for (String featureFile : features) {
            printlnCyan("Running the functional tests from " + featureFile);
            printlnCyan("Locating feature file in " + FILE_DIRECTORY + featureFile + FEATURE_FILE_EXTENSION);

            List<Feature> featureFromFile = featureParser.features(FILE_DIRECTORY + featureFile + FEATURE_FILE_EXTENSION);
            printlnCyan("Feature file successfully parsed");

            executeFeatures(featureFile, featureFromFile, webDriver);
            featuresExecuted.addAll(featureFromFile);
        }
        return featuresExecuted;
    }

    private void displayExecutionTime(Date startTime, Date endTime) {
        long nanoTime = endTime.getTime() - startTime.getTime();
        long diffSeconds = nanoTime / 1000 % 60;
        long diffMinutes = nanoTime / (60 * 1000) % 60;
        long diffHours = nanoTime / (60 * 60 * 1000) % 24;
        printlnCyan("Total execution time " + diffHours + "h:" + diffMinutes + "m:" + diffSeconds + "s");
    }

    private Report generateHTMLReport(List<Feature> featureListForReport) {
        Report report = new Report(featureListForReport);
        report = anorocReporting.generateReport(report);
        return report;
    }

    private void executeFeatures(String featureFile, List<Feature> features, WebDriver webDriver) {
        commandExecutor
                .withWebDriver(webDriver)
                .withXPaths(FILE_DIRECTORY + featureFile + JSON_FILE_EXTENSION);
        printlnCyan("Started executing functional tests on feature " + featureFile);
        features.forEach(
                feature -> {
                    printlnYellow("\tFeature: " + feature.getName());
                    executeScenarioOutlines(feature);
                }
        );
        printlnCyan("Completed executing feature " + featureFile);
    }

    private void executeScenarioOutlines(Feature feature) {
        feature.getScenarioOutlines().forEach(scenarioOutline -> {
            printlnYellow("\t\tScenario Outline: " + scenarioOutline.getName());
            executeStepDefinition(scenarioOutline);
        });
    }

    private void executeStepDefinition(ScenarioOutline scenarioOutline) {
        scenarioOutline.getStepDefinitions().forEach(stepDefinition -> {
            commandExecutor.execute(stepDefinition);
            updateScreenShots(stepDefinition);
            displayResult(stepDefinition);
        });
    }

    private void updateScreenShots(StepDefinition stepDefinition) {
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
            printlnGreen(result.toString());
        } else {
            printlnRed(result.toString());
            printlnRed(stepDefinition.getResult().getErrorMessage());
        }
    }
}
