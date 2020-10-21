package com.publicissapient.anoroc.messaging.payload;

import com.publicissapient.anoroc.model.FeatureType;
import com.publicissapient.anoroc.model.RunStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RunPayload {

    private long runId;

    private FeatureType featureType;

    private long featureId;

    private String name;

    private String content;

    private String xpath;

    private RunStatus status;

    private String reportPath;

    private String environment;

    private String errorDescription;

    private Map<String, Object> args = new HashMap<>();

    private List<RunPayload> includeFeatureRunPayload = new ArrayList<>();

    private String applicationName;

}
