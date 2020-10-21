package com.publicissapient.anoroc.model.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.publicissapient.anoroc.model.Feature;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@Getter
@Setter
public class FeatureReport {

    @JsonProperty("elements")
    private List<ScenarioOutlineReport> scenarioOutlines;

    @JsonProperty("keyword")
    private String keyword = "Feature";

    @JsonProperty("name")
    private String name;

    @JsonProperty("uri")
    private String uri = "";

    public FeatureReport(String name) {
        this.name = name;
        scenarioOutlines = new ArrayList<>();
    }

    public static FeatureReport build(String name) {
        return new FeatureReport(name);
    }

    private void FeatureReport() {
        scenarioOutlines = new ArrayList<>();
    }

    public FeatureReport buildScenarioOutlines(Feature feature) {
        feature.getScenarioOutlines().stream().forEach(scenarioOutline -> {
            scenarioOutlines
                    .add(ScenarioOutlineReport.build(scenarioOutline.getName())
                            .withData(scenarioOutline));
        });
        return this;
    }

}
