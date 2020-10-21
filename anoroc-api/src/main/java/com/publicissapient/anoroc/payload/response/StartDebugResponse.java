package com.publicissapient.anoroc.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class StartDebugResponse {

    @JsonProperty("session_id")
    private String sessionId;

    private String message;
}
