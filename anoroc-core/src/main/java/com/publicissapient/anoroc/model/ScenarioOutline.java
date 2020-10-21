package com.publicissapient.anoroc.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScenarioOutline {

    private String name;

    private List<StepDefinition> stepDefinitions = new ArrayList<>();

    public ScenarioOutline(String name) {
        this.name = name;
    }

    public static ScenarioOutline build(String name) {
        return new ScenarioOutline(name);
    }

    public void addStepDefinition(StepDefinition stepDefinition) {
        stepDefinitions.add(stepDefinition);
    }

}
