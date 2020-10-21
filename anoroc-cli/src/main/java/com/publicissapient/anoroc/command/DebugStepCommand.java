package com.publicissapient.anoroc.command;

import com.publicissapient.anaroc.executor.CommandExecutor;
import com.publicissapient.anaroc.factory.WebDriverFactory;
import com.publicissapient.anoroc.model.StepDefinition;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.publicissapient.anoroc.printer.ConsolePrinter.*;

@ShellComponent
public class DebugStepCommand {

    @Autowired
    private CommandExecutor commandExecutor;

    private WebDriver webDriver;

    @ShellMethod("Starts a debug session.")
    public void startDebug() {
        try {
            printlnYellow("Starting a debugging session");
            webDriver = WebDriverFactory.localFireFoxWebDriver();
            printlnCyan("Debugging session in progress");
        } catch (Exception e) {
            printlnRed(e.getMessage());
        }
    }

    @ShellMethod("Stop running debug session.")
    public void stopDebug() {
        try {
            printlnYellow("Stopping a debugging session");
            webDriver.quit();
            printlnCyan("Debugging session complete");
        } catch (Exception e) {
            printlnRed(e.getMessage());
        }
    }

    @ShellMethod("Debug steps in a feature line by line.")
    public void debug(String step, @ShellOption(defaultValue = "") String data, @ShellOption(defaultValue = "") String xPath) {
        startDebugSessionIfNotRunning();
        try {
            StepDefinition stepDefinition = executeStepDefinition(step, data, xPath);
            dumpDebugData(stepDefinition, xPath);
            displayResult(step, stepDefinition);
        } catch (Exception e) {
            printlnRed(e.getMessage());
        }
    }

    private void dumpDebugData(StepDefinition stepDefinition, String xPath) {
        printlnCyan("Debugging info");
        printlnCyan("****************");
        printlnCyan("Step: " + stepDefinition.getInstruction());
        printlnCyan("Data:");
        stepDefinition.getData().forEach((key, value) -> printlnCyan(value));
        printlnCyan(xPath);
        printlnCyan("****************");
    }

    private StepDefinition executeStepDefinition(String step, String data, String xPath) {
        String stepKey = getStepKey(step);
        Map<String, String> xPaths = getXPathData(data, xPath, stepKey);

        StepDefinition stepDefinition = StepDefinition.build()
                .withInstruction(step)
                .withData(stepKey, data);

        commandExecutor
                .withWebDriver(webDriver)
                .withXPaths(xPaths)
                .execute(stepDefinition);
        return stepDefinition;
    }

    private void displayResult(String step, StepDefinition stepDefinition) {
        StringBuilder result = new StringBuilder()
                .append(step)
                .append(" ")
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

    private void startDebugSessionIfNotRunning() {
        if (!checkIfDebugSessionIsStarted()) {
            printlnRed("Could not find the debugging session, starting a new one");
            startDebug();
        } else {
            printlnYellow("Current debug session is active, using the same");
        }
    }

    private Map<String, String> getXPathData(String data, String xPath, String stepKey) {
        Map<String, String> xPaths = new HashMap<>();
        if ("".equals(xPath)) {
            xPaths.put(stepKey, data);
        } else {
            xPaths.put(stepKey, xPath);
        }
        return xPaths;
    }

    private String getStepKey(String step) {
        int startIndex = 0;
        int endIndex = 0;
        if (step.contains("<") && step.contains(">")) {
            startIndex = step.indexOf("<") + 1;
            endIndex = step.indexOf(">");
        } else if (step.contains("[") && step.contains("]")) {
            startIndex = step.indexOf("[") + 1;
            endIndex = step.indexOf("]");
        }
        return step.substring(startIndex, endIndex);
    }

    private boolean checkIfDebugSessionIsStarted() {
        return webDriver != null;
    }
}
