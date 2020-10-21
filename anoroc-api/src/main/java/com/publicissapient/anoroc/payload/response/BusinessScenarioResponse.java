package com.publicissapient.anoroc.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessScenarioResponse {

    private long id;

    private String name;

    private String description;

    @JsonProperty(value = "created_date_time")
    private LocalDateTime createdDateTime;

    @JsonProperty(value = "updated_date_time")
    private LocalDateTime updatedDateTime;

    @JsonProperty(value = "features")
    List<FeatureResponse> features;

}
