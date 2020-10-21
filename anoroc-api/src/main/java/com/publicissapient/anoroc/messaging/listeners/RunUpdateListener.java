package com.publicissapient.anoroc.messaging.listeners;

import com.publicissapient.anoroc.messaging.payload.RunPayload;
import com.publicissapient.anoroc.service.RunService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RunUpdateListener {

    @Autowired
    RunService runService;

    @JmsListener(destination = "${anoroc.activemq.run.update.queue}")
    public void process(RunPayload runPayload) {
        log.debug("Received run payload to update "+runPayload.getName());
        //update back run with status
        runService.updateRun(runPayload);
    }
}
