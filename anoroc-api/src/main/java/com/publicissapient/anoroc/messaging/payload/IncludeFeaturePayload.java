package com.publicissapient.anoroc.messaging.payload;


import com.publicissapient.anoroc.model.RunStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class IncludeFeaturePayload {

    private long featureId;

    private String name;

    private String content;

    private String xpath;

    private RunStatus status;

    private String reportPath;

    private Map<String, Object> args;
}
