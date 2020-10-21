package com.publicissapient.anoroc.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentResponse {

    private long id;

    private String name;

    @JsonProperty(value = "karate_content")
    private String karateContent;

    @JsonProperty(value = "anoroc_content")
    private String anorocContent;

    @JsonProperty(value = "created_date_time")
    private LocalDateTime createdDateTime;

    @JsonProperty(value = "updated_date_time")
    private LocalDateTime updatedDateTime;

    @JsonProperty(value = "settings_content")
    private String settingsContent;

}
