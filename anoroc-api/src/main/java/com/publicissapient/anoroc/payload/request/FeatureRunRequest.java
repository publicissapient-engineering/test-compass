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
public class FeatureRunRequest {

    @JsonProperty("feature_id")
    private long featureId;

    @JsonProperty("env_id")
    private long environmentId;

    @JsonProperty("global_object_id")
    private long globalObjectId;

}
