package com.publicissapient.anoroc.service;

import com.publicissapient.anoroc.messaging.payload.PayloadPublisher;
import com.publicissapient.anoroc.model.Run;
import com.publicissapient.anoroc.model.RunType;

public interface PayLoadPublisher {

    public Run saveAndPublishToQueue(PayloadPublisher request) throws Exception;

    public boolean isRunTypeMatches(RunType runType);

}
