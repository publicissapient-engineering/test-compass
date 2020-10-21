package com.publicissapient.anoroc.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchResponse {

    private Long id;

    private String name;

    private String description;

    private List<BusinessScenarioResponse> businessScenarios;

    @JsonProperty(value="created_at")
    private LocalDateTime createdAt;

    @JsonProperty(value = "updated_at")
    private LocalDateTime updatedAt;

    private ApplicationResponse application;

}
