package com.publicissapient.anoroc.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentRequest {

    private long id;

    private String name;

    @JsonProperty(value = "karate_content")
    private String karateContent;

    @JsonProperty(value = "anoroc_content")
    private String anorocContent;

    @JsonProperty(value = "settings_content")
    private String settingsContent;
}
