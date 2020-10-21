package com.publicissapient.anaroc.executor;

import com.publicissapient.anaroc.parser.XPathParser;
import com.publicissapient.anoroc.model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandExecutorFeatureTest {

    public static final String VALID_GOOGLE_SEARCH_JSON = "src/test/resources/xpaths/google-search.json";
    public static final String INVALID_GOOGLE_SEARCH_JSON = "src/test/resources/xpaths/invalid-google-search.json";

    private static WebDriver webDriver;

    @BeforeAll
    static void beforeAll() throws Exception {
        webDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), new FirefoxOptions());
    }

    @AfterAll
    static void afterAll() {
        webDriver.quit();
    }

    @Test
    void should_run_google_search_feature() {
        List<Feature> features = buildFeatures();
        Map<String, String> xPaths = XPathParser.xpaths(VALID_GOOGLE_SEARCH_JSON);

        CommandExecutor commandExecutor = CommandExecutor.build(webDriver, xPaths);
        verifyResults(commandExecutor.execute(features));
    }

    @Test
    void should_return_failed_and_error_message_for_execution_failure() {
        List<Feature> features = buildFeatures();
        Map<String, String> xPaths = XPathParser.xpaths(INVALID_GOOGLE_SEARCH_JSON);

        CommandExecutor commandExecutor = CommandExecutor.build(webDriver, xPaths);
        verifyFailureResults(commandExecutor.execute(features));
    }

    @Test
    void should_execute_single_step_definition_without_data() {
        Map<String, String> xPaths = XPathParser.xpaths(VALID_GOOGLE_SEARCH_JSON);
        CommandExecutor commandExecutor = CommandExecutor.build(webDriver, xPaths);
        StepDefinition stepDefinition = StepDefinition.build()
                .withKeyword("Given").withInstruction("I OPEN [url]");
        commandExecutor.execute(stepDefinition);
        assertThat(stepDefinition.getResult().getStatus()).isEqualTo(Status.PASSED);
        assertThat(stepDefinition.getResult().getErrorMessage()).isNull();
        assertThat(stepDefinition.getResult().getDurationInNano()).isGreaterThan(0);
    }

    @Test
    void should_execute_step_definition_with_data() {
        Map<String, String> xPaths = XPathParser.xpaths(VALID_GOOGLE_SEARCH_JSON);
        CommandExecutor commandExecutor = CommandExecutor.build(webDriver, xPaths);
        StepDefinition stepDefinition = StepDefinition.build()
                .withKeyword("Given").withInstruction("I OPEN [url]");
        commandExecutor.execute(stepDefinition);

        stepDefinition = StepDefinition.build()
                .withKeyword("And")
                .withInstruction("I ENTER <search_term> in the text box")
                .withData("search_term", "hello");
        commandExecutor.execute(stepDefinition);
        assertThat(stepDefinition.getResult().getStatus()).isEqualTo(Status.PASSED);
        assertThat(stepDefinition.getResult().getErrorMessage()).isNull();
        assertThat(stepDefinition.getResult().getDurationInNano()).isGreaterThan(0);
    }

    private void verifyFailureResults(List<Feature> features) {
        List<Result> results = getResults(features);
        assertThat(results.stream().allMatch(result -> result.getStatus() == Status.FAILED)).isTrue();
        assertThat(results.stream().allMatch(result -> result.getErrorMessage().isEmpty())).isFalse();
        assertThat(results.stream().allMatch(result -> result.getDurationInNano() > 0)).isTrue();
    }

    private void verifyResults(List<Feature> features) {
        List<Result> results = getResults(features);
        assertThat(results.stream().allMatch(result -> result.getStatus() == Status.PASSED)).isTrue();
        assertThat(results.stream().allMatch(result -> result.getDurationInNano() > 0)).isTrue();
    }

    private List<Result> getResults(List<Feature> features) {
        return features.stream()
                .flatMap(feature -> feature.getScenarioOutlines().stream()
                        .flatMap(scenarioOutline -> scenarioOutline.getStepDefinitions().stream()
                                .map(StepDefinition::getResult)))
                .collect(Collectors.toList());
    }

    private List<Feature> buildFeatures() {
        Feature feature = new Feature("Search on google");
        ScenarioOutline scenarioOutline = ScenarioOutline
                .build("Successful search for publicis sapient on google");

        scenarioOutline.getStepDefinitions().add(StepDefinition.build()
                .withKeyword("Given")
                .withInstruction("that I am OPEN [url]"));
        scenarioOutline.getStepDefinitions().add(StepDefinition.build()
                .withKeyword("And")
                .withInstruction("I ENTER <search_term> in the text box")
                .withData("search_term", "hello"));
        scenarioOutline.getStepDefinitions().add(StepDefinition.build()
                .withKeyword("When")
                .withInstruction("I CLICK [search_button]"));
        scenarioOutline.getStepDefinitions().add(StepDefinition.build()
                .withKeyword("Then")
                .withInstruction("I should SEE [search_results]"));
        feature.getScenarioOutlines().add(scenarioOutline);
        return Collections.singletonList(feature);
    }
}
