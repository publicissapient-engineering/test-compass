package com.publicissapient.anoroc.payload.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.publicissapient.anoroc.model.RunType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RunDetail {

    private long id;

    private String details;

    @JsonProperty("run_at")
    private LocalDateTime runAt;

    private String status;

    @JsonProperty(value = "run_type")
    private RunType runType;

    @JsonProperty("report_url")
    private String reportUrl;

    private String errorDescription;

}
