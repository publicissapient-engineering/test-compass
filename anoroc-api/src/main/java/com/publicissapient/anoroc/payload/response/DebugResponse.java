package com.publicissapient.anoroc.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.publicissapient.anoroc.model.ScreenShot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public class DebugResponse {


    private String status;

    @JsonProperty("duration_in_nano")
    private long durationInNano;

    @JsonProperty("error_message")
    private String errorMessage;


    @JsonProperty("screen_shots")
    private List<ScreenShot> screenShots = new ArrayList<>();
}
