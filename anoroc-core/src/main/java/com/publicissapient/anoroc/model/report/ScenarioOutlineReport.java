package com.publicissapient.anoroc.model.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.publicissapient.anoroc.model.ScenarioOutline;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ScenarioOutlineReport {
    @JsonProperty("keyword")
    private String keyword = "Scenario";

    @JsonProperty("type")
    private String type = "scenario";

    @JsonProperty("name")
    private String name;

    @JsonProperty("steps")
    private List<StepDefinitionReport> steps;

    private ScenarioOutlineReport(String name) {
        this.name = name;
        this.steps = new ArrayList<>();
    }

    public static ScenarioOutlineReport build(String name) {

        return new ScenarioOutlineReport(name);
    }

    public ScenarioOutlineReport withData(ScenarioOutline scenario) {

        scenario.getStepDefinitions().stream().forEach(stepDefinition -> {
            steps.add(
                    StepDefinitionReport.build(stepDefinition.getInstruction())
                            .withSteps(stepDefinition));
        });
        return this;
    }

}
