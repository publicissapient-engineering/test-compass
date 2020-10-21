package com.publicissapient.anoroc.payload.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessScenarioRunRequest {

    @JsonProperty("business_scenario_id")
    private long businessScenarioId;

    @JsonProperty("env_id")
    private long environmentId;

    @JsonProperty("global_object_id")
    private long globalObjectId;
}
