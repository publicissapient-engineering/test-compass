package com.publicissapient.anoroc.messaging.payload;


import com.publicissapient.anoroc.model.RunStatus;
import com.publicissapient.anoroc.model.RunType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessScenarioRunPayload {

    private long runId;

    private long businessScenarioId;

    private RunType runType;

    private boolean isAnyFeatureHasFailedStatus;

    private RunStatus status;

    private String reportUrl;

    private long environmentId ;


}
