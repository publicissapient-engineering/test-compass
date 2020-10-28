package com.publicissapient.anoroc;

import com.publicissapient.anoroc.exception.FeatureFileNotFoundException;
import com.publicissapient.anoroc.model.Feature;
import com.publicissapient.anoroc.model.ScenarioOutline;
import com.publicissapient.anoroc.model.StepDefinition;
import com.publicissapient.anoroc.parser.FeatureParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class FeatureParserTest {

    public static final String SINGLE_FEATURE_SINGLE_SCENARIO_FILE = "src/test/resources/features/single-feature-single-scenario-outline-with-example.feature";
    public static final String SINGLE_FEATURE_MULTIPLE_SCENARIO_FILE = "src/test/resources/features/single-feature-multiple-scenario-outline-with-example.feature";
    public static final String SINGLE_FEATURE_SINGLE_SCENARIO_NO_EXAMPLES_FILE = "src/test/resources/features/single-feature-with-scenario-outline-no-examples.feature";
    public static final String FILE_THAT_IS_NOT_AVAILABLE = "src/test/resources/features/featureTest.feature";

    @Test
    void should_return_file_not_found_exception() {
        Assertions.assertThrows(FeatureFileNotFoundException.class, () -> new FeatureParser()
                .features(FILE_THAT_IS_NOT_AVAILABLE));
    }

    @Test
    void should_return_feature_from_single_feature_single_scenario_file() {
        List<Feature> features = new FeatureParser().features(SINGLE_FEATURE_SINGLE_SCENARIO_FILE);
        assertThat(features.size()).isEqualTo(1);
        assertThat(features.get(0)).isEqualToComparingOnlyGivenFields(expectedFeature(), "name");
    }

    @Test
    void should_return_feature_from_single_feature_single_scenario_source() {
        List<Feature> features = new FeatureParser().features(featureContent(),"Login Testing");
        assertThat(features.size()).isEqualTo(1);
        assertThat(features.get(0)).isEqualToComparingOnlyGivenFields(expectedFeature(), "name");
    }

    @Test
    void should_return_feature_with_scenario_from_single_scenario_file() {
        List<Feature> features = parseSingleFeatureSingleScenarioFile();
        assertThat(features.get(0).getScenarioOutlines().get(0))
                .isEqualToComparingOnlyGivenFields(expectedScenarioOutline(), "name");
    }

    @Test
    void should_return_scenario_with_steps_from_single_scenario_file() {
        List<Feature> features = parseSingleFeatureSingleScenarioFile();
        List<StepDefinition> stepDefinitions = parseStepDefinitions(features);
        assertThat(stepDefinitions).isEqualTo(expectedStepDefinitionsForSingleFeatureSingleScenarioOutline());
    }

    @Test
    void should_return_features_from_multiple_feature_multiple_scenario_outline_and_examples_file() {
        List<Feature> features = parseMultipleFeatureMultipleScenarioFile();
        List<StepDefinition> stepDefinitions = parseStepDefinitions(features);
        assertThat(stepDefinitions.size()).isEqualTo(25);
    }

    @Test
    void should_return_step_definitions_for_no_example_file() {
        List<Feature> features = new FeatureParser().features(SINGLE_FEATURE_SINGLE_SCENARIO_NO_EXAMPLES_FILE);
        List<StepDefinition> stepDefinitions = parseStepDefinitions(features);
        assertThat(stepDefinitions.size()).isEqualTo(5);
    }

    private List<Feature> parseMultipleFeatureMultipleScenarioFile() {
        return new FeatureParser().features(SINGLE_FEATURE_MULTIPLE_SCENARIO_FILE);
    }

    private List<StepDefinition> parseStepDefinitions(List<Feature> features) {
        return features.stream()
                .flatMap(feature -> feature.getScenarioOutlines().stream()
                        .flatMap(scenarioOutline -> scenarioOutline.getStepDefinitions().stream()))
                .collect(Collectors.toList());
    }

    private List<Feature> parseSingleFeatureSingleScenarioFile() {
        return new FeatureParser().features(SINGLE_FEATURE_SINGLE_SCENARIO_FILE);
    }

    private ScenarioOutline expectedScenarioOutline() {
        return new ScenarioOutline("Login scenario into gmail");
    }

    private Feature expectedFeature() {
        return new Feature("This is a sample login testing feature");
    }

    private List<StepDefinition> expectedStepDefinitionsForSingleFeatureSingleScenarioOutline() {
        return Arrays.asList(
                StepDefinition
                        .build()
                        .withKeyword("Given ")
                        .withInstruction("that I OPEN [url]"),
                StepDefinition
                        .build()
                        
                StepDefinition
                        .build()
                        .withKeyword("And ")
                        .withInstruction("I ENTER <password>")
                        .withData("password", "password1"),
                StepDefinition
                        .build()
                        .withKeyword("When ")
                        .withInstruction("I CLICK [submit]"),
                StepDefinition
                        .build()
                        .withKeyword("Then ")
                        .withInstruction("I should SEE [dashboard]"),
                StepDefinition
                        .build()
                        .withKeyword("Given ")
                        .withInstruction("that I OPEN [url]"),
                StepDefinition
                        .build()
                        .withKeyword("And ")
                        .withInstruction("I ENTER <username> <password>")
                        .withData("username", "raghu@ps.com")
                        .withData("password", "password2"),
                StepDefinition
                        .build()
                        .withKeyword("And ")
                        .withInstruction("I ENTER <password>")
                        .withData("password", "password2"),
                StepDefinition
                        .build()
                        .withKeyword("When ")
                        .withInstruction("I CLICK [submit]"),
                StepDefinition
                        .build()
                        .withKeyword("Then ")
                        .withInstruction("I should SEE [dashboard]")
        );
    }


    private String featureContent() {
        return "Feature: This is a sample login testing feature\n" +
                "\n" +
                "  Scenario Outline: Login scenario into gmail\n" +
                "    Given that I OPEN [url]\n" +
                "    And I ENTER <username> <password>\n" +
                "    And I ENTER <password>\n" +
                "    When I CLICK [submit]\n" +
                "    Then I should SEE [dashboard]\n" +
                "    Examples:\n" +
                "      |   username        |   password      |\n" +
                "      |   venki@ps.com    |  password1      |\n" +
                "      |   raghu@ps.com    |  password2      |";
    }
}
