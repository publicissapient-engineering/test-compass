package com.publicissapient.anoroc.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Feature {

    private final List<ScenarioOutline> scenarioOutlines;

    private final String name;

    public static Feature build(String name) {
        return new Feature(name);
    }

    public Feature(String name) {
        this.name = name;
        this.scenarioOutlines = new ArrayList<>();
    }

}
