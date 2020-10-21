package com.publicissapient.anoroc.factory;


import com.publicissapient.anoroc.messaging.payload.PayloadPublisher;
import com.publicissapient.anoroc.model.RunType;

public class PayloadPublisherFactoryTest {

    public static PayloadPublisher getPayloadPublisher(long id, long envId, long globalObjId, RunType runType) {
        return PayloadPublisher.builder().payloadId(id).environmentId(envId).globalObjectId(globalObjId).runType(runType).build();
    }
}
