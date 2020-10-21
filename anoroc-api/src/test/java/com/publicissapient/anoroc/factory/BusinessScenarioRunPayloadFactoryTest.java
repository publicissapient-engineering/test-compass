package com.publicissapient.anoroc.factory;

import com.publicissapient.anoroc.messaging.payload.BusinessScenarioRunPayload;

public class BusinessScenarioRunPayloadFactoryTest {

    public static BusinessScenarioRunPayload getBusinessScenarioRunPayload() {
        return BusinessScenarioRunPayload.builder().businessScenarioId(1l).runId(1l).isAnyFeatureHasFailedStatus(false).build();
    }
}
