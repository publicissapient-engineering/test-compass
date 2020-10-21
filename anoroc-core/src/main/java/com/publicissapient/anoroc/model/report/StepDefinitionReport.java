package com.publicissapient.anoroc.model.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.publicissapient.anoroc.model.Result;
import com.publicissapient.anoroc.model.StepDefinition;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StepDefinitionReport {

    @JsonProperty("keyword")
    private String keyword;

    @JsonProperty("name")
    private String name;

    @JsonProperty("result")
    private ResultReport result;

    @JsonProperty("match")
    private Match match;

    @JsonProperty("embeddings")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ScreenshotEmbedding> screenshotEmbeddingList;

    public StepDefinitionReport(String name) {
        this.name = name;
        this.screenshotEmbeddingList = new ArrayList<>();
    }

    public static StepDefinitionReport build(String name) {
        return new StepDefinitionReport(name);
    }

    public StepDefinitionReport withSteps(StepDefinition stepDefinition) {
        replaceInstructionWithData(stepDefinition);
        this.keyword = stepDefinition.getKeyword();
        this.result = getResult(stepDefinition.getResult());
        this.match = new Match(stepDefinition.getInstruction().replace(" ", "_"));
        buildScreenShotEmbedding(stepDefinition.getResult());
        return this;
    }

    private void buildScreenShotEmbedding(Result result) {
        if(result !=null && result.getScreenShots() !=null) {
            result.getScreenShots().stream()
                    .forEach(screenShot -> this.screenshotEmbeddingList.add(ScreenshotEmbedding
                            .build(screenShot.getData()).withData(screenShot)));
        }
    }

    private void replaceInstructionWithData(StepDefinition stepDefinition) {
        if (!stepDefinition.getData().isEmpty()) {
            for (String key : stepDefinition.getData().keySet()) {
                this.name = this.name.replace("<" + key + ">", stepDefinition.getData().get(key));
            }
        }
    }

    private ResultReport getResult(Result result){
        if (result == null) {
          return  ResultReport.build("Failed");
        }
       return ResultReport.build(result.getStatus().getLabel()).withData(result);
    }

    @Setter
    @Getter
    public class Match {

        @JsonProperty("location")
        private String location;

        public Match(String locationName) {
            this.location = locationName;
        }
    }

}
