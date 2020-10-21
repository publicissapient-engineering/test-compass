package com.publicissapient.anoroc.factory;

import com.publicissapient.anoroc.messaging.payload.RunPayload;
import com.publicissapient.anoroc.model.FeatureType;
import com.publicissapient.anoroc.model.RunStatus;

public class RunPayLoadFactoryTest {

    public static RunPayload getRunPayLoad(){
        return RunPayload.builder().runId(1l).featureId(1l).featureType(FeatureType.KARATE).reportPath("/url").status(RunStatus.RUNNING).content(null).name("FeatureRun").build();
    }
}
