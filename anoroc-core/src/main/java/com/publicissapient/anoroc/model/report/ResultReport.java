package com.publicissapient.anoroc.model.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.publicissapient.anoroc.model.Result;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultReport {

    @JsonProperty("status")
    private String status;

    @JsonProperty("duration")
    private long durationInNano = 0L;

    @JsonProperty("error_message")
    private String errorMessage;

    public ResultReport(String name) {
        this.status = name;
    }

    public static ResultReport build(String status){
        return new ResultReport(status);
    }

    public ResultReport withData(Result result){
        this.durationInNano = result.getDurationInNano();
        this.errorMessage = result.getErrorMessage();
        return this;
    }

}
